#include <bits/stdc++.h>
using namespace std;

ifstream fin("anhviet109K.txt");
ofstream fout1("words.txt");
ofstream fout2("pronounces.txt");
ofstream fout3("meaning.txt");
ofstream fout4("dictionaries.txt");

struct Word
{
    string word, ipa, meaning;

    bool operator<(const Word &other) const
    {
        return word < other.word;
    }
};

string line;
bool isNewWord = true;
set<char> ss;
set<string> words;
bool is_a_word = true;
vector<Word> fullWords;

int main()
{
    fin.ignore();
    while (getline(fin, line))
    {
        if (line.empty())
        {
            isNewWord = true;
            continue;
        }
        if (isNewWord)
        {
            is_a_word = true;
            line.erase(line.begin());
            int _cnt = 0;
            string word, pronounce;
            for (int i = 0; i < line.size(); i++)
            {
                if (line[i] == '/')
                {
                    _cnt++;
                }
                if (_cnt == 0)
                {
                    word += line[i];
                }
                else
                {
                    pronounce += line[i];
                }
                if (_cnt == 2)
                {
                    break;
                }
            }
            while (word.back() == ' ')
                word.pop_back();
            // while (word.front() == ' ')
            //     word.erase(word.begin());

            for (char c : word)
            {
                if (!isalpha(c) && c != ' ' && c != '-' && c != '.' && c != '\'')
                {
                    is_a_word = false;
                    break;
                }
            }

            if (is_a_word)
            {
                for (char &c : word)
                {
                    c = tolower(c);
                    ss.insert(c);
                }
                if (words.count(word))
                {
                    is_a_word = false;
                }
                else
                {
                    words.insert(word);
                    fullWords.push_back({word, pronounce, ""});
                }
            }
            isNewWord = false;
        }
        else
        {
            if (is_a_word)
            {
                fullWords.back().meaning += line;
                fullWords.back().meaning += '\\';
            }
        }
    }
    sort(fullWords.begin(), fullWords.end());
    for (auto s : fullWords)
    {
        // fout1 << s.word << '\n';
        // fout2 << s.ipa << '\n';
        // fout3 << s.meaning << '\n';

        fout4 << s.word;
        fout4 << '\t';
        fout4 << s.meaning << '\n';
    }
    cout << ss.size() << '\n';
    for (char c : ss)
    {
        cout << c << '\n';
    }
}