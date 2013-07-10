try:
    from setuptools import setup, find_packages
except ImportError:
    from distutils.core import setup, find_packages

config = {
    'description': 'OSIAM Connector for Python -- enables easy access to OSIAM services',
    'author': 'Philipp Eder',
    'url': 'http://github.com/osiam/connector4python',
    'author_email': 'p.eder@tarent.de',
    'version': '0.1',
    'install_requires': ['nose', 'requests', 'flask', 'mock'],
#    'packages': ['osiam'],
    'packages':find_packages(exclude=['performance']),
    'scripts': [],
    'name': 'OSIAM Sample Client'
}

setup(**config)
