#include <stdio.h>
#include <string.h>
#include "my_str.h"

int main() {
    char TXT[1024];
    char word[30];
    printf("pleae enter your word\n");
    int letter_in_word=0;
    char ch2;
    ch2= getchar();
    while (ch2!=' ' && ch2!='\t' && ch2!='\n'){
        word[letter_in_word]=ch2;
        ch2=getchar();
        letter_in_word++;
    }
    word[letter_in_word]='\0';
    printf("pleae enter your text\n");
    int counter_letter_text=0;
    char ch;
    ch = getchar();
    while(ch!= '~'){
        TXT[counter_letter_text]=ch;
        ch = getchar();
        counter_letter_text++;
    }
    TXT[counter_letter_text]='\0';
    printf("Gematria Sequences: ");
    funcA(word,TXT);
    printf("\n");
    printf("Atbash Sequences: ");
    funcB(word,TXT);
    printf("\n");
    printf("Anagram Sequences: ");
    funcC(word,TXT);
    return 0;
}
