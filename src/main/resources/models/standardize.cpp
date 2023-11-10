#include <bits/stdc++.h>
using namespace std;

wifstream fin("anhviet109K.txt");
wofstream fout1("words.txt");
wofstream fout2("pronounces.txt");
wofstream fout3("meaning.txt");
wofstream fout4("dictionaries.txt");

struct Word
{
    wstring word, ipa, meaning;

    bool operator<(const Word &other) const
    {
        return word < other.word;
    }
};

wstring line;
bool isNewWord = true;
set<wchar_t> ss;
set<wstring> words;
bool is_a_word = true;
vector<Word> fullWords;

int main()
{
    fin.ignore();
    set<wchar_t> allStart;
    while (getline(fin, line))
    {
        if (line.empty())
        {
            isNewWord = true;
            continue;
        }

        if (line[0] == '!' && allStart.find(line[0]) == allStart.end()) {
            wcout << line << '\n';
        }
        allStart.insert(line[0]);

        if (isNewWord)
        {
            is_a_word = true;
            line.erase(line.begin());
            int _cnt = 0;
            wstring word, pronounce;
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

            for (wchar_t c : word)
            {
                if (!isalpha(c) && c != ' ' && c != '-' && c != '.' && c != '\'')
                {
                    is_a_word = false;
                    break;
                }
            }

            if (is_a_word)
            {
                for (wchar_t &c : word)
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
                    fullWords.push_back({word, pronounce, L""});
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
        wcout << c << '\n';
    }

    cout << "Start of lines:\n";
    for (auto c : allStart)
    {
        wcout << c << '\n';
    }
}