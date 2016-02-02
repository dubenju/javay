c:\mingw\libexec\gcc\mingw32\4.5.2\cc1.exe cp2img.c -mtune=i386 -march=i386 -o cp2img.s
c:\mingw\mingw32\bin\as.exe -o cp2img.o cp2img.s
C:\MinGW\bin\ld.exe -Bdynamic -o cp2img.exe c:/mingw/lib/crt2.o c:/mingw/lib/gcc/mingw32/4.5.2/crtbegin.o -Lc:/mingw/lib/gcc/mingw32/4.5.2 -Lc:/mingw/lib/ cp2img.o  -lmingw32 -lgcc_eh -lgcc -lmoldname -lmingwex -lmsvcrt -luser32 -lkernel32 -ladvapi32 -lshell32 c:/mingw/lib/gcc/mingw32/4.5.2/crtend.o
