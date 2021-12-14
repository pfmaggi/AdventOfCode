#include <stdio.h>
#include <stdlib.h>

int main(void) {
    FILE *fp = fopen("../input/day02.txt", "r");
    if (!fp) {
        perror("File opening failed");
        return EXIT_FAILURE;
    }
    long total_paper = 0;
    long position = 0;
    long i = 0;
    int c;
    int w, h, l;
    while ((c = fscanf(fp,"%dx%dx%d[^\n]", &w, &h, &l)) != EOF) {
        int b1 = w*h;
        int b2 = w*l;
        int b3 = h*l;
        int min = (b1<b2)?b1:b2;
        min = (min<b3)?min:b3;
        total_paper += min + 2*(b1+b2+b3);

        printf("w=%d - h=%d - l=%d # min=%d # paper=%ld\n", w, h, l, min, total_paper);
    }
    fclose(fp);

    printf("total paper=%ld\n", total_paper);

    return EXIT_SUCCESS;
}
