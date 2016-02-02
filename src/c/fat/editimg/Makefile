CC = C:/MinGW/bin/gcc.exe
CC1 = C:/MinGW/libexec/gcc/mingw32/4.5.0/cc1.exe -quiet -mtune=i386 -march=i386 -std=c99 
AS  = C:/MinGW/bin/as.exe
default:
#	$(CC) -B -std=c99 -v -o editimg.exe editimg.c
	$(CC) -B -include ../readfat.h -std=c99 -v -o editimg.exe editimg.c
#	$(CC1) -H -include ./readfat.h -std=c99 -v -o readfat.exe readfat.c

asm :
	$(CC1) -o editimg.gas editimg.c
obj :
	$(AS) -o editimg.obj editimg.gas
