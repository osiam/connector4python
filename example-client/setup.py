try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

config = {
    'description': 'My Project',
    'author': 'Philipp Eder',
    'url': 'http://github.com/osiam-dev/osiam',
    'author_email': 'p.eder@tarent.de',
    'version': '0.1',
    'install_requires': ['nose', 'requests', 'flask', 'mock'],
    'packages': ['connector'],
    'scripts': [],
    'name': 'OSIAM NG Example Client'
}

setup(**config)
