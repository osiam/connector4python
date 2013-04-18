__author__ = 'phil'

from flask import Flask, request
from requests.auth import HTTPBasicAuth
import requests
app = Flask(__name__)

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
    return r.content


@app.route('/oauth2')
def oauth2_response():
    code = request.args.get('code')
    if code is not None:
        return auth_code_to_access_token(code)
    else:
        return request.args

if __name__ == '__main__':
    app.run(debug=True)
