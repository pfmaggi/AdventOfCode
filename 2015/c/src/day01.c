#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    if (argc < 2) {
        printf("Please enter filename to input data:\n");
        printf("> %s <filename>\n", argv[0]);
        return EXIT_FAILURE;
    }

    // Open the file
    FILE *fp = fopen(argv[1], "r");
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

    printf("AoC2015 - Day01\n===============\n");
    printf("Final floor is: %ld\n", count);
    printf("Entered the basement at position: %ld\n", position);

    return EXIT_SUCCESS;
}
