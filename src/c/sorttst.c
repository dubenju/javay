#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void debug_array(int ary[], int cnt) {
    int i;
    printf("--------------------\n");
    for (i = 0; i < cnt; i ++) {
        printf("%d", ary[i]);
        if ((i + 1) % 10 == 0) {
            printf("\n");
        } else {
            printf(",");
        }
    }
    if (i % 10 != 0) {
        printf("\n");
    }
    printf("--------------------\n");
}

int rand_array(int ary[], int count) {
    int i;
    for (i = 0; i < count; i ++) {
        ary[i] = rand();
    }
    return 0;
}

/* 1945 */
void insertion_sort(int a[], int len) {
    int j;
    for (j = 1; j < len; j ++) {
        int key = a[j];
        int i = j - 1;
        while (i >= 0 && a[i] > key) {  
            a[i + 1] = a[i];
            i --;
        }
        a[i + 1] = key;
    }
}

void merge(int* array , int first , int mid , int last) {

    int* arr = NULL;
    int i = first , j =mid+1 ,lenth = last-first+1,p = 0 ;
    arr = (int*)malloc(lenth*sizeof(int));
    if (arr == NULL) {
        printf("memory alloc error.");
        exit(0);
    }

    while (i <= mid || j <= last) {
        if (i <= mid && j <= last) {
            arr[p++] = (array[i]<=array[j])?array[i++]:array[j++];
            /* if (array[i] >= array[j]) {
                 arr[p] = array[j];
                 p++;
                 j++;
             } else {
                 arr[p] = array[i];
                 p++;
                 i++;
             }*/
        } else if (i <= mid && j > last)  {
            arr[p++] = array[i++];
        } else {
            arr[p++] = array[j++];
        }
    }
    for (i = 0,j = first ; i < lenth ; i++,j++)  {
        array[j] = arr[i];
    }
    free(arr);
    arr = NULL;
}


/* 1945 */
void merge_sort(int array[], unsigned int first, unsigned int last) {
    int mid = 0;
    if(first < last) {
        /*mid = (first+last)/2;*/
        /*mid = first/2 + last/2;*/
        /*mid = i(first & last) + (first ^ last) >> 1);*/
        mid = ((first & last) + ((first ^ last) >> 1));
        merge_sort(array, first, mid);
        merge_sort(array, mid + 1,last);
        merge(array, first, mid, last);
    }
}

/* 1956 */
void bubble_sort(int arr[], int count) {
    int i = count, j;
    int temp;
 
    while(i > 0) {
        for(j = 0; j < i - 1; j++) {
            if(arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        i--;
    }
}

void selection_sort(int *a, int len) {
    int i, j, min, t;
    for(i = 0; i < len - 1; i ++) {
        min = i;
        for(j = i + 1; j < len; j ++) {
            if(a[min] > a[j]) {
                min = j;
            }
        }
        if(min != i) {
            t = a[min];
            a[min] = a[i];
            a[i] = t;
        }
    }
}

/* 1959 */
void shell_sort(int *a, int len) {
    int h = 1;
    int j;
    while( h < len ) {
        h = 3 * h + 1;
    }
    while(h > 0) {  
        for (j = h; j < len; j ++) {
            int key = a[j];
            int i = j - h;
            while( i >= 0 && a[i] > key ) {
                a[i + h] = a[i];
                i = i - h;
            }
            a[i + h] = key;
        }
        h = h / 3;
    }
}


void swap(int *a, int *b) {
    int tmp;
    tmp = *a; *a = *b; *b = tmp;
}

/* 1962 */ 
void quick_sort(int a[], int left, int right) {
    int i = left + 1, j = right;
    int  key = a[left];
 
    if (left >= right) return;
 
    while (1) {
       while (a[j] > key) j --;
       while (a[i] < key&&i<j) i ++;
       if(i >= j) break;
       swap(&a[i], &a[j]);
       if(a[i] == key) j --;
       else  i++;
    }
 
    swap(&a[left], &a[j]);
 
    if(left  < i - 1)   quick_sort(a, left, i - 1);
    if(j + 1 < right)  quick_sort(a, j + 1 , right);
}

/* 1964 */
void max_heapify(int *a, int i, int heapSize) {
    int l = (i+1)*2-1;  
    int r = (i+1)*2;  
    int largest;  
  
    if (l<=heapSize && a[l]>a[i])  
        largest = l;  
    else  
        largest = i;  
  
    if (r<=heapSize && a[r]>a[largest])  
        largest = r;  
  
    if (largest!=i) {  
        swap(&a[i], &a[largest]);  
        max_heapify(a, largest, heapSize);  
    }
}
  
void build_max_heap(int *a, int len) {
    int i;
    for (i=len/2-1; i>=0; i--) {
        max_heapify(a, i, len-1);
    }
}
  
void heap_sort(int *a, int len) {
    int i;
    build_max_heap(a, len);
    for (i = len-1; i > 0; i --) {
        swap(&a[0], &a[i]);
        max_heapify(a, 0, i - 1);
    }
}

double get_time(clock_t start, clock_t end) {
    double timeUsed;

    timeUsed = (double) (end - start) / CLOCKS_PER_SEC;
    printf("use time %f sec.\n", timeUsed);

    return timeUsed;
}

int test_sort(const int count) {
    clock_t start, end;
    double timeUsed;
    int * p, * sort_data;

    p = (int *)malloc(sizeof(int) * count);
    if (p == NULL) {
        printf("memory alloc error.\n");
        return -1;
    }
    sort_data = (int *)malloc(sizeof(int) * count);
    if (sort_data == NULL) {
        printf("memory alloc error.\n");
        return -1;
    }
    memset(p, 0, sizeof(int) * count);
    rand_array(p, count);

    /* shell sort */
    memset(sort_data, 0, sizeof(int) * count);
    memcpy(sort_data, p, sizeof(int) * count);
/*    debug_array(sort_data, count);*/
    start = clock();
    shell_sort(sort_data, count);
    end = clock();
/*    debug_array(sort_data, count);*/
    printf("shell  sort:");
    get_time(start, end);

    /* quick sort */
    memset(sort_data, 0, sizeof(int) * count);
    memcpy(sort_data, p, sizeof(int) * count);
/*    debug_array(sort_data, count);*/
    start = clock();
    quick_sort(sort_data, 0, count - 1);
    end = clock();
/*    debug_array(sort_data, count);*/
    printf("quick  sort:");
    get_time(start, end);

    /* heap sort */
    memset(sort_data, 0, sizeof(int) * count);
    memcpy(sort_data, p, sizeof(int) * count);
/*    debug_array(sort_data, count);*/
    start = clock();
    heap_sort(sort_data, count);
    end = clock();
/*    debug_array(sort_data, count);*/
    printf("heap   sort:");
    get_time(start, end);

    if (p != NULL) {
         free(p);
         p = NULL;
    }
    if (sort_data != NULL) {
         free(sort_data);
         sort_data = NULL;
    }
    return 0;
}

int main(int argc, char * argv[]) {

/*
    test_sort(300000);
printf("\n");
    test_sort(400000);
printf("\n");
    test_sort(500000);
printf("\n");
    test_sort(600000);
printf("\n");
    test_sort(700000);
printf("\n");
    test_sort(800000);
printf("\n");
    test_sort(900000);
printf("\n");
*/

    test_sort(3000000);
printf("\n");
    test_sort(4000000);
printf("\n");
    test_sort(5000000);
printf("\n");
    test_sort(6000000);
printf("\n");
    test_sort(7000000);
printf("\n");
    test_sort(8000000);
printf("\n");
    test_sort(9000000);
printf("\n");

    return 0;
}
