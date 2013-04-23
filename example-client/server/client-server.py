__author__ = 'phil'
import sys
import ast
from flask import Flask, request, render_template, redirect
from requests.auth import HTTPBasicAuth
import requests
import urllib
import json
from osiam import connector
import logging

app = Flask(__name__)

authZServer = 'http://localhost:8080/authorization-server'
client_id = 'testClient'
redirect_uri = 'http://localhost:5000/oauth2'
scopes = 'POST PUT GET DELETE PATCH'
params = {'response_type': 'code', 'state': 'state', 'client_id': client_id,
          'redirect_uri': redirect_uri, 'scope': scopes}
oauth2_auth_code = '/oauth/authorize?{0}'.format(urllib.urlencode(params))

access_token = None
response = []
uuids = []
mode = 'user'
scim = None


def auth_code_to_access_token(code):
    param = {
        'code': code,
        'grant_type': 'authorization_code',
        'redirect_uri': 'http://localhost:5000/oauth2'
    }
    r = requests.post('http://localhost:8080/authorization-server/oauth/token',
                      auth=HTTPBasicAuth('testClient', 'secret'),
                      params=param)
    global access_token, response, scim
    print 'response: ' + r.content
    access_token = json.loads(r.content).get('access_token')
    response.append(r.content)
    scim = connector.SCIM(authZServer, access_token)
    return redirect('/')


@app.route('/', methods=['POST'])
def switch_mode():
    global mode
    mode = request.form.get('selected')
    print 'switched to:{0}'.format(mode)
    return redirect("/")


@app.route('/oauth2')
def oauth2_response():
    code = request.args.get('code')
    if code is not None:
        print 'got auth code: ' + code
        return auth_code_to_access_token(code)
    else:
        return request.args


@app.route('/create/User')
def redirect_create_user():
    return render_template('create_user.html')


def build_user():
    return connector.SCIMUser(
        #   schemas = request.form.get('schema'),
        userName=request.form.get('user_name'),
        #                          name = name,
        displayName=request.form.get('displayname'),
        nickName=request.form.get('nickname'),
        profileUrl=request.form.get('Profileurl'),
        title=request.form.get('title'),
        userType=request.form.get('usertype'),
        preferredLanguage=request.form.get('preferredlanguage'),
        locale=request.form.get('locale'),
        timezone=request.form.get('timezone'),
        active=True,
        password=request.form.get('password'))


def call_scim_set_response(func, *args):
    global response, scim
    result = func(*args)
    response.append(result)
    try:
        uuids.append(result.id)
    except AttributeError:
        print 'attribute has no id field ...'
    return redirect('/')


@app.route('/create/User', methods=['POST'])
def create_user():
    return call_scim_set_response(scim.create_user, build_user())


@app.route('/replace/User')
def redirect_replace_user():
    return render_template('replace_user.html', uuids=uuids)


@app.route('/replace/User', methods=['POST'])
def replace_user():
    return call_scim_set_response(scim.replace_user, request.form.get('uuid'),
                                  build_user())


@app.route('/update/User')
def redirect_update_user():
    return render_template('update_user.html', uuids=uuids)


@app.route('/update/User', methods=['POST'])
def update_user():
    return call_scim_set_response(scim.update_user, request.form.get('uuid'),
                                  build_user())


@app.route('/delete/User')
def redirect_delete_user():
    return render_template('delete_user.html')


@app.route('/delete/User', methods=['POST'])
def delete_user():
    return call_scim_set_response(scim.delete_user, request.form.get('uuid'))


@app.route('/get/User')
def redirect_get_user():
    return render_template('get_user.html')


@app.route('/get/User', methods=['POST'])
def get_user():
    return call_scim_set_response(scim.get_user,  request.form.get('uuid'))


def build_group():
    members = []
    fm = request.form.get('members')
    if str(fm) is not '':
        for s in fm.split('\n'):
            logging.info('Line:{}'.format(s))
            d = ast.literal_eval(s)
            members.append(d)
    return connector.SCIMGroup(displayName=request.form.get('displayname'),
                               members=members)


@app.route('/create/group')
def redirect_create_group():
    return render_template('create_group.html')


@app.route('/create/group', methods=['POST'])
def create_group():
    return call_scim_set_response(scim.create_group, build_group())


@app.route('/replace/group')
def redirect_replace_group():
    return render_template('replace_group.html', uuids=uuids)


@app.route('/replace/group', methods=['POST'])
def replace_group():
    return call_scim_set_response(scim.replace_group, request.form.get('uuid'),
                                  build_group())


@app.route('/update/group')
def redirect_update_group():
    return render_template('update_group.html', uuids=uuids)


@app.route('/update/group', methods=['POST'])
def update_group():
    return call_scim_set_response(scim.update_group, request.form.get('uuid'),
                                  build_group())


@app.route('/delete/group')
def redirect_delete_group():
    return render_template('delete_group.html')


@app.route('/delete/group', methods=['POST'])
def delete_group():
    # will crash need to write method to get an id ....
    return call_scim_set_response(scim.delete_group, request.form.get('uuid'))


@app.route('/get/group')
def redirect_get_group():
    return render_template('get_group.html')


@app.route('/get/group', methods=['POST'])
def get_group():
    # will crash need to write method to get an id ....
    return call_scim_set_response(scim.get_group,  request.form.get('uuid'))


@app.route('/redirect')
def redirect_to_oauth2_server():
    return redirect(authZServer + oauth2_auth_code)


@app.route('/')
def show_entries():
    return render_template('index.html', access_token=access_token,
                           response=response)


if __name__ == '__main__':
    global authZServer, redirectUri
    if sys.argv[1] is not None:
        authZServer = sys.argv[1]
    if sys.argv[2] is not None:
        redirectUri = sys.argv[2]

    print 'redirect uri is {}'.format(redirectUri)
    print 'AuthZ-Server is {}'.format(authZServer)
    app.run(host='0.0.0.0', debug=True)
