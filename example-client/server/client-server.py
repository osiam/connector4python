__author__ = 'phil'

from flask import Flask, request, render_template, redirect
from requests.auth import HTTPBasicAuth
import requests
import urllib
import json
from connector import osiam

from sqlite3 import dbapi2 as sqlite3
app = Flask(__name__)

authZServer = 'http://localhost:8080/authorization-server'
client_id = 'testClient'
redirect_uri = 'http://localhost:5000/oauth2'
scopes = 'POST PUT GET DELETE PATCH'
params = {'response_type' : 'code', 'state' : 'state', 'client_id' : client_id, 'redirect_uri' : redirect_uri,
          'scope' : scopes}
oauth2_auth_code = '/oauth/authorize?{0}'.format(urllib.urlencode(params))

access_token = None
response = None
mode = 'user'
scim = None

def auth_code_to_access_token(code):
    param = {
        'code' : code,
        'grant_type' : 'authorization_code',
        'redirect_uri' : 'http://localhost:5000/oauth2'
    }
    r = requests.post('http://localhost:8080/authorization-server/oauth/token',
        auth=HTTPBasicAuth('testClient', 'secret'),
        params=param
    )
    global access_token, response, scim
    print 'response: '+r.content
    access_token = json.loads(r.content).get('access_token')
    response = r.content
    scim = osiam.SCIM(authZServer, access_token)
    return redirect('/')


@app.route('/', methods=['POST'])
def switch_mode():
    global mode
    mode = request.form.get('selected');
    print 'switched to:{0}'.format(mode)
    return redirect("/")

@app.route('/get/User')
def get_resource():
    global response, scim
    response = scim.get_user(request.args.get('uuid'))
    return redirect('/')

@app.route('/oauth2')
def oauth2_response():
    code = request.args.get('code')
    if code is not None:
        print 'got auth code: '+code
        return auth_code_to_access_token(code)
    else:
        return request.args

@app.route('/create/User')
def redirect_create_user():
    return render_template('create_user.html')

@app.route('/create/User', methods=['POST'])
def create_user():
#     <p><label>Firstname:<input id="firstname" name="firstname" type="text" value="Arthur"></label></p>
#    <p><label>Lastname:<input id="lastname" name="lastname" type="text" value="Dent"></label></p>
    user = osiam.SCIMUser(schemas = request.form.get('schema'),
                          userName = request.form.get('user_name'),
#                          name = name,
#                           displayName = request.form.get('displayname'),
#                           nickName = request.form.get('nickname'),
#                           profileUrl = request.form.get('Profileurl'),
#                           title = request.form.get('title'),
#                           userType = request.form.get('usertype'),
#                           preferredLanguage = request.form.get('preferredlanguage'),
#                           locale = request.form.get('locale'),
#                           timezone = request.form.get('timezone'),
#                          active = True,
                          password = request.form.get('password'))
    global response, scim
    response = scim.create_user(user)
    return redirect('/')

@app.route('/redirect')
def redirect_to_oauth2_server():
    return redirect(authZServer+oauth2_auth_code)

@app.route('/')
def show_entries():
    return render_template('index.html', access_token = access_token, response = response)



if __name__ == '__main__':
    app.run(debug=True)
