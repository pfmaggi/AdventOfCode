#include <stdio.h>
#include <stdlib.h>

int main(void) {
    FILE *fp = fopen("../input/day01.txt", "r");
    if (!fp) {
        perror("File opening failed");
        return EXIT_FAILURE;
    }
    long count = 0;
    long position = 0;
    long i = 0;
    int c;
    while ((c = fgetc(fp)) != EOF) {
        i++;
        if ('(' == (char)c) count++;
        if (')' == (char)c) count--;
        if ((0 == position) && (count < 0)) position = i;
    }
    fclose(fp);

    printf("Final floor is: %ld\n", count);
    printf("Entered the basement at position: %ld\n", position);

    return EXIT_SUCCESS;
}
