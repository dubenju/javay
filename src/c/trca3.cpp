#include <iostream>
#include <windows.h>

DWORD WINAPI thread_func(LPVOID pN)
{
    try{
    for (int i = 0; i < *((int*)pN); ++i) {
        std::cout << i+1 << "\t";
    }
    std::cout << std::endl;
    throw "ok.";
    }
    catch (const char* s) {
        std::cerr << s << std::endl;
        exit(1);
    }

    std::cout << "thread_func() done." << std::endl;
    return 0;
}

int main(int argc, char* argv[])
{
    HANDLE hThrd;
    DWORD thrdId;
    int n = 5;

    hThrd = CreateThread(    NULL,
                            0,
                            thread_func,
                            (LPVOID)&n,
                            0,
                            &thrdId);
    Sleep(2000);

    std::cout << "main() done." << std::endl;

    return 0;
}