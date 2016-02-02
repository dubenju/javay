APP=coff
#RES=dll

GCC=mingw32-gcc.exe
G++=mingw32-g++.exe

# c => asm
CC1=/mingw/libexec/gcc/mingw32/4.8.1/cc1.exe

# cpp => asm
CPP=/mingw/libexec/gcc/mingw32/4.8.1/cc1plus.exe

# asm => obj
ASC=as.exe

# asm => obj
#ASM = nasm.exe
ASM = ML.exe

#CLL=/mingw/libexec/gcc/mingw32/4.8.1/collect2.exe
# obj => exe,dll
CLL=ld.exe

# obj => lib(static)
AR=ar.exe

# delete
rm=rm

# rc => obj
RCC=windres.exe

# rc => res
RC=rc.exe

#res =>obj
CVR=cvtres.exe

$(APP): $(APP).o
	$(CLL) \
	--subsystem console \
	-Bdynamic \
	-o$(APP).exe \
	/mingw/mingw32/lib/crt2.o \
	/mingw/lib/gcc/mingw32/4.8.1/crtbegin.o \
	-L/usr/local/lib/ \
	-L/mingw/lib/ \
	-L./lib/ \
	-L/mingw/lib/gcc/mingw32/4.8.1 \
	-L/mingw/lib/gcc \
	-L/mingw/mingw32/lib \
	./obj/$(APP).o \
	-lmingw32 \
	-lgcc \
	-lgcc_eh \
	-lmoldname \
	-lmingwex \
	-lmsvcrt \
	-lgdi32 \
	-lcomdlg32 \
	-ladvapi32 \
	-lshell32 \
	-luser32 \
	-lkernel32 \
	-lmingw32 \
	-lgcc \
	-lgcc_eh \
	-lmoldname \
	-lmingwex \
	-lmsvcrt \
	-lstdc++ \
	-lpsapi \
	-lws2_32 \
	-lz \
	/mingw/lib/gcc/mingw32/4.8.1/crtend.o

#
dll: $(APP).o
	$(CLL) --subsystem windows \
	--shared \
	-Bdynamic \
	-e _DllMainCRTStartup@12 \
	--enable-auto-image-base \
	-u ___register_frame_info \
	-u ___deregister_frame_info \
	-o ./tdl/$(APP).dll \
	/mingw/mingw32/lib/dllcrt2.o \
	/mingw/lib/gcc/mingw32/4.8.1/crtbegin.o \
	-L/mingw/lib/gcc/mingw32/4.8.1 \
	-Lc:/mingw/lib/gcc \
	-Lc:/mingw/mingw32/lib \
	-Lc:/mingw/lib/ \
	$(APP).o \
	--enable-stdcall-fixup \
	--output-def $(APP).def \
	--out-implib lib$(APP).a \
	-lmingw32 \
	-lgcc \
	-lgcc_eh \
	-lmoldname \
	-lmingwex \
	-lmsvcrt \
	-lgdi32 \
	-lcomdlg32 \
	-ladvapi32 \
	-lshell32 \
	-luser32 \
	-lkernel32 \
	-lmingw32 \
	-lgcc \
	-lgcc_eh \
	-lmoldname \
	-lmingwex \
	-lmsvcrt \
	/mingw/lib/gcc/mingw32/4.8.1/crtend.o

lib: $(APP).o
	$(AR) rcs ./tdl/liblib.a $(APP).o

$(APP).o: $(APP).s
	$(ASC) -v -o ./obj/$(APP).o $(APP).s

$(APP).obj:
	$(ASM) -o ./obj/$(APP).obj -f coff -l ./obj/$(APP).lst $(APP).asm
	$(ASM) /c /Cp /coff /Fl./obj/$(APP).lst /Fo./obj/$(APP).obj $(APP).asm

$(APP).s:
	$(CC1) -quiet -v -iprefix c:/mingw/libexec/gcc/mingw32/4.8.1/ --include-directory=C:/MinGW/mingw32/lib/gcc/mingw32/4.8.1/include --include-directory=C:/MinGW/mingw32/include --include-directory=C:/MinGW/include --include-directory=C:/MinGW/include $(APP).c -mwindows -mtune=generic -march=pentiumpro -auxbase $(APP) -version -o $(APP).s

$(APP)rc.o :
	$(RCC) $(RES).rc -o ./obj/$(APP)rc.o
	$(RC) /fo ./obj/$(APP)rc.o $(RES).rc
	$(CVR) /machine:ix86 /out:./obj/$(APP)rc.obj ./obj/$(APP)rc.res

clean :
	$(rm) $(APP).exe ./obj/$(APP).o $(APP).s
default1 :
	mingw32-g++.exe dll.c -shared -o dll.dll -Wl,--enable-stdcall-fixup,--output-def,mydll.def,--out-implib,mylib.a -mwindows -v
