#include <iostream>
#include <windows.h>

DWORD WINAPI thread_func(LPVOID pN)
{
    for (int i = 0; i < *((int*)pN); ++i) {
        std::cout << i+1 << "\t";
    }
    std::cout << std::endl;
    throw "ok.";

    std::cout << "thread_func() done." << std::endl;
    return 0;
}

int main(int argc, char* argv[])
{
    int n = 5;

    try{
    thread_func((LPVOID)&n);
    Sleep(2000);
    }
    catch (const char* s) {
        std::cerr << s << std::endl;
        exit(1);
    }

    std::cout << "main() done." << std::endl;

    return 0;
}
