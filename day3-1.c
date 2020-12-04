#include <stdio.h>
#include <stdlib.h>
#include <string.h>

unsigned int countLines(FILE *fp) {
    char c;
    unsigned int count = 0;
    rewind(fp);
    while((c=fgetc(fp)) != EOF) {
        if(c == '\n')
            ++count;
    }
    rewind(fp);
    return count;
}

void binprint(unsigned int n)
{
    
    for (int i = 0; i < 32; i++) {
        if (n & (1<<31))
            printf("1");
        else
            printf("0");

        n <<= 1;
    }
    printf("\n");
}

unsigned int getit(unsigned int * lines, unsigned int numLines) {
    char slide = 31;
    unsigned int trees = 0;
    for (int i = 0; i<=numLines; i++) {
        if(lines[i] & (1<<slide)) {
            ++trees;
        }
        //binprint(1<<slide);
        //printf("%d\n", slide);
        slide -= 3;
        
        if(slide <= 0)
            slide = 31 + slide;
        
    }
    return trees;
}

int main() {
    FILE *fp = fopen("day3_input.txt", "r");
    if(fp == NULL) {
        perror("Failed to open file");
        return 1;
    };
    //looks like input is 32 char long including \n
    char line[33];
    unsigned int *lines;
    lines = calloc(countLines(fp), sizeof(*lines));
    unsigned long len = 0;
    unsigned int lineNum = 0;
    while (fgets(line, sizeof(line)/sizeof(*line), fp) != NULL) {
        len = strlen(line) - 1;
        if(len == 0)
            break; //trailing newline?
        printf("%s", line);
        for(int i=0; i < len; i++) {
            char bit = 0;
            if(line[i] == '#')
                bit = 1;
            __asm__ volatile (""
            "shr $1, %1;"
            "rcl $1, %0;"
            "" : "=m"(lines[lineNum]) : "r" (bit));
        }
        lines[lineNum] <<= 1; //input is only 31 long
        binprint(lines[lineNum]);
        ++lineNum;
    }
    unsigned int trees = getit(lines, lineNum);
    printf("Hit %u trees!\n", trees);
    fclose(fp);
    return 0;
}
