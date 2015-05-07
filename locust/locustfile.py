# coding=utf8
import codecs
import random
import urllib

from locust import HttpLocust, TaskSet, task

languages = [ 'bg', 'bs', 'cs', 'da', 'de', 'el', 'en', 'es', 'et', 'fi', 'fr',
              'ga', 'hr', 'hu', 'is', 'it', 'lb', 'lt', 'lv', 'mk', 'mt', 'nl',
              'no', 'pl', 'pt', 'rm', 'ro', 'sk', 'sl', 'sq', 'sr', 'sv', 'tr' ]
fl = [
    'geometry',
    'name',
    'name_{lang}',
    'admunit1_name',
    'admunit1_name_{lang}',
    'admunit2_name',
    'admunit2_name_{lang}'
]

qf = {
    'name_exact': 20,
    'names_exact': 15,
    'name': 10,
    'names': 1
}

path = '/gazetteer'
core = 'gazetteer'
handler = 'query'

with codecs.open('names.txt', encoding='utf-8') as f:
    names = f.read().splitlines()

def create_url(language, q):
    return
    return path + '/' + core + '/' + handler + '?' + urllib.urlencode(param)


class User(TaskSet):
    def on_start(self):
        self.language = random.choice(languages)
        self.url = path + '/' + core + '/' + handler
        self.qf = ' '.join([str.format('{}^{}', key, qf[key]) for key in qf])
        self.df = ','.join([key for key in qf])

    @task
    def request(self):
        params = self.create_params()
        with self.client.get(self.url, params=params, name=self.url, catch_response=True) as response:
            self.process_response(response)

    def create_params(self):
        return {
            'defType': 'dismax',
            'echoParams': 'none',
            'indent': 'false',
            'wt': 'json',
            'rows': 10,
            'qf': self.qf,
            'df': self.df,
            'fl': ','.join([str.format(field, lang=self.language) for field in fl]),
            'q': random.choice(names).encode('utf-8')
        }

    def process_response(self, response):
        try:
            content = response.json()
            if content is not None:
                status = content['responseHeader']['status']
                if status != 0:
                    message = content['error']['msg']
                    response.failure(message)
        except:
            pass

class WebsiteUser(HttpLocust):
    task_set = User
    min_wait = 500
    max_wait = 2000
