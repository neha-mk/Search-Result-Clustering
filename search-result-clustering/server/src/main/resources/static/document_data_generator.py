import random
import nltk
import requests
import os

# Download the English words corpus from nltk
nltk.download('words')

from nltk.corpus import words

def get_random_wikip_topics(topics_filepath, no_of_topics):

    # Specify the path to the file containing Wikipedia topics
    file_path =  topics_filepath #"Wikipedia_topics/Wikipedia_topics"
    
    with open(file_path, 'r', encoding='utf-8') as file:
        all_topics = file.readlines()

    all_topics = [topic.strip() for topic in all_topics]

    # Filter topics to include only English words
    english_words = set(words.words())
    english_topics = [topic for topic in all_topics if all(word.lower() in english_words for word in topic.split())]

    # Check if there are at least no_of_topics English topics
    if len(english_topics) < no_of_topics:
        raise "The file does not contain enough English topics."
    else:
        # Randomly selection
        random_topics = random.sample(english_topics, no_of_topics)

    return random_topics

# Fetch and and writes the document to the directory
def fetch_write_doc(topic, write_path):
    text_config = {
        'action': 'query',
        'format': 'json',
        'titles': topic,
        'prop': 'extracts',
        'exintro': True,
        'explaintext': True,
    }
    text_response = requests.get('https://en.wikipedia.org/w/api.php',params=text_config).json()
    
    # to avoid any error due to get request
    if 'query' not in text_response and 'pages' not in text_response['query']:
        return
        
    text_page = next(iter(text_response['query']['pages'].values()))
    
    # check if request has extract and the extract has data
    if 'extract' not in text_page:
        return
    
    if not text_page['extract']:
        return
    
    file1 = write_path + text_page['title'] + ".txt"
    
    with open(file1, "w", encoding='utf-8', errors='ignore') as file:
        file.write(text_page['extract'])


def generate_documents(topics_filepath, no_of_documents, write_path):
    topics = get_random_wikip_topics(topics_filepath, no_of_documents)
    
    os.makedirs(write_path, exist_ok=True)
    
    for i in topics:
        fetch_write_doc(i, write_path)

if __name__ == "__main__":
    topics_filepath = "Wikipedia_topics/Wikipedia_topics"
    no_of_documents = 2500
    write_path = "Documents/"
    generate_documents(topics_filepath, no_of_documents, write_path)