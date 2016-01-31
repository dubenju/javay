#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <limits.h>
#include <assert.h>
#include <zlib.h>

/* 
 *  gcc -o compress.exe compress.c -lmman -lz
 */ 
int compress_file (const char *filename) {
  int src_fd, dest_fd; 
  struct stat sb; 
  Bytef *src, *dest; 
  uLong dest_len; 
  char dest_file[PATH_MAX]; 

  src_fd = open (filename, O_RDONLY); 
  assert (dest_fd != -1); 

  assert (fstat (src_fd, &sb) != -1); 

  src = mmap (NULL, sb.st_size, PROT_READ, MAP_PRIVATE, src_fd, 0); 
  assert (src != MAP_FAILED); 

  dest_len = compressBound (sb.st_size); 
  dest = malloc (dest_len); 
  assert (dest); 

  assert (compress (dest, &dest_len, src, sb.st_size) == Z_OK); 

  munmap (src, sb.st_size); 
  close (src_fd); 

  snprintf (dest_file, sizeof (dest_file), "%s.z", filename); 
  dest_fd = creat (dest_file, S_IRUSR | S_IWUSR); 
  assert (dest_fd != -1); 

  write (dest_fd, dest, dest_len); 
  close (dest_fd); 
  free (dest); 

  return 0; 
}

int uncompress_file (const char *filename) {
  int src_fd, dest_fd; 
  struct stat sb; 
  Bytef *src, *dest; 
  uLong dest_len; 
  char dest_file[PATH_MAX]; 

  src_fd = open (filename, O_RDONLY); 
  assert (dest_fd != -1); 

  assert (fstat (src_fd, &sb) != -1); 

  src = mmap (NULL, sb.st_size, PROT_READ, MAP_PRIVATE, src_fd, 0); 
  assert (src != MAP_FAILED); 

  dest_len = compressBound (sb.st_size); 
  dest = malloc (dest_len); 
  assert (dest); 

  assert (compress (dest, &dest_len, src, sb.st_size) == Z_OK); 
    if(uncompress(text, &tlen, buf, blen) != Z_OK)  
    {  
        printf("uncompress failed!\n");  
        return -1;  
    }  

  munmap (src, sb.st_size); 
  close (src_fd); 

  snprintf (dest_file, sizeof (dest_file), "%s.z", filename); 
  dest_fd = creat (dest_file, S_IRUSR | S_IWUSR); 
  assert (dest_fd != -1); 

  write (dest_fd, dest, dest_len); 
  close (dest_fd); 
  free (dest); 

  return 0; 
}


int main(int argc, char * argv[]) {
    
    return compress_file("c:\\prj\\apl\\log\\20140621l.txt");
}

