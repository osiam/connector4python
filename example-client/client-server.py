__author__ = 'phil'

from flask import Flask, request, render_template, redirect
from osiam import connector
from requests.auth import HTTPBasicAuth
import argparse
import ast
import json
import logging
import requests
import urllib

logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

parser = argparse.ArgumentParser(description='This is an example client to' +
                                 'show the usage of the osiam-connector and ' +
                                 'for demonstration purpose only. It is, by ' +
                                 'all means, not suited for production.')
parser.add_argument('-r', '--redirect', help='A OSIAM known redirect uri.',
                    default='http://localhost:5000/oauth2')
parser.add_argument('-o', '--osiam', help='The uri to OSIAM.',
                    default='http://localhost:8080/osiam-server')
parser.add_argument('-c', '--client', help='The name of the client.',
                    default='example-client')
parser.add_argument('-s', '--client-secret', help='The name of the client.',
                    default='secret')


app = Flask(__name__)

scopes = 'POST PUT GET DELETE PATCH'
params = None
oauth2_auth_code = None

access_token = None
response = None
uuids = []
mode = 'user'
scim = None


def auth_code_to_access_token(code):
    param = {
        'code': code,
        'grant_type': 'authorization_code',
        'redirect_uri': redirect_uri
    }
    r = requests.post('{}/oauth/token'.format(authZServer),
                      auth=HTTPBasicAuth(client, client_secret),
                      params=param, verify=False)
    global access_token, response, scim
    print 'response: ' + r.content
    access_token = json.loads(r.content).get('access_token')
    response = r.content
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
    userMeta = None
    lastName = None
    firstName = None

    if request.form.get('meta') is not None:
        userMeta = connector.Meta(attributes=request.form.get('meta').split())

    if request.form.get('lastname') is not None:
        lastName = request.form.get('lastname')

    if request.form.get('firstname') is not None:
        firstName = request.form.get('firstname')

    return connector.SCIMUser(
        externalId=request.form.get('external_id'),
        userName=request.form.get('user_name'),
        name=connector.SCIMName(familyName=lastName,
                                givenName=firstName),
        displayName=request.form.get('displayname'),
        nickName=request.form.get('nickname'),
        profileUrl=request.form.get('Profileurl'),
        title=request.form.get('title'),
        userType=request.form.get('usertype'),
        preferredLanguage=request.form.get('preferredlanguage'),
        locale=request.form.get('locale'),
        timezone=request.form.get('timezone'),
        active=True,
        password=request.form.get('password'),
        meta=userMeta)


def call_scim_set_response(func, *args):
    global response, scim
    result = func(*args)
    response = result
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

    groupMeta = None
    if request.form.get('meta') is not None:
        groupMeta = connector.Meta(attributes=request.form.get('meta').split())

    return connector.SCIMGroup(displayName=request.form.get('displayname'),
                               members=members, meta=groupMeta)


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


def call_search_on_osiam_server(func):
    if request.form.get('method') == 'get':
        return call_scim_set_response(func, request.form.get('params'))
    elif request.form.get('method') == 'post':
        dict = ast.literal_eval(request.form.get('params'))
        return call_scim_set_response(func, dict)


@app.route('/search/User')
def redirect_search_user():
    return render_template('search_user.html')


@app.route('/search/User', methods=['POST'])
def search_user():
    if request.form.get('method') == 'get':
        return call_search_on_osiam_server(scim.search_with_get_on_users)
    elif request.form.get('method') == 'post':
        return call_search_on_osiam_server(scim.search_with_post_on_users)


@app.route('/search/Group')
def redirect_search_group():
    return render_template('search_group.html')


@app.route('/search/Group', methods=['POST'])
def search_group():
    if request.form.get('method') == 'get':
        return call_search_on_osiam_server(scim.search_with_get_on_groups)
    elif request.form.get('method') == 'post':
        return call_search_on_osiam_server(scim.search_with_post_on_groups)


@app.route('/search/Root')
def redirect_search_root():
    return render_template('search_root.html')


@app.route('/search/Root', methods=['POST'])
def search_root():
    if request.form.get('method') == 'get':
        return call_search_on_osiam_server(scim.search_with_get_on_root)
    elif request.form.get('method') == 'post':
        return call_search_on_osiam_server(scim.search_with_post_on_root)


def build_client():
    return connector.Client(
        id=request.form.get('client_id'),
        accessTokenValiditySeconds=request.form.get(
            'accessTokenValiditySeconds'),
        refreshTokenValiditySeconds=request.form.get(
            'refreshTokenValiditySeconds'),
        redirectUri=request.form.get('redirect_uri'),
        scope=request.form.get('scope').split())


@app.route('/get/Client')
def redirect_get_client():
    return render_template('get_client.html')


@app.route('/get/Client', methods=['POST'])
def get_client():
    return call_scim_set_response(scim.get_client, request.form.get('client_id'))


@app.route('/create/Client')
def redirect_create_client():
    return render_template('create_client.html')


@app.route('/create/Client', methods=['POST'])
def create_client():
    return call_scim_set_response(scim.create_client, build_client())


@app.route('/delete/Client')
def redirect_delete_client():
    return render_template('delete_client.html')


@app.route('/delete/Client', methods=['POST'])
def delete_client():
    return call_scim_set_response(scim.delete_client, request.form.get('client_id'))


if __name__ == '__main__':
    args = parser.parse_args()
    client = args.client
    client_secret = args.client_secret
    authZServer = args.osiam
    redirect_uri = args.redirect
    params = {'response_type': 'code', 'state': 'state',
              'client_id': client,
              'redirect_uri': redirect_uri, 'scope': scopes}
    oauth2_auth_code = '/oauth/authorize?{0}'.format(urllib.urlencode(params))
    print 'redirect uri is {}'.format(redirect_uri)
    print 'AuthZ-Server is {}'.format(authZServer)
    app.run(host='0.0.0.0', debug=True)
