#include <stdio.h>
#include <stdlib.h>
#include <string.h> 

/* File Header 20 bytes */
/* Stores the basic information about the file as well as pointers to the other structures. */
typedef struct _tag_FILEHDR_{
    unsigned short usMachine;      // 2Target Machine Magic number 
    unsigned short usNumSec;       // 2Number Of Sections
    unsigned long  ulTime;         // 4Time & Date Stamp This field is of the type time_t, being the number of seconds since epoch ( 1970-01-01 00:00:00 GMT ).
    unsigned long  ulSymbolOffset; // 4File pointer to Symbol Table
    unsigned long  ulNumSymbol;    // 4NumberOfSymbols
    unsigned short usOptHdrSZ;     // 2SizeOfOptionalHeader
    unsigned short usFlags;        // 2Characteristics
} FILEHDR, * PFILEHDR;

void display_coff_header(PFILEHDR pCoffHeader) {
    printf("Target Machine           =0X%04X.", pCoffHeader->usMachine);
    switch(pCoffHeader->usMachine) {
        case 0x0000: printf("UNKNOWN\n"); break;
        case 0x0184: printf("Alpha AXP\n"); break;
        case 0x01c0: printf("ARM\n"); break;
        case 0x0284: printf("Alpha AXP 64-bit.\n"); break;
        case 0x014c: printf("Intel 386 or later.\n"); break;
        case 0x0200: printf("Intel IA64.\n"); break;
        case 0x0268: printf("Motorola 68000 series.\n"); break;
        case 0x0266: printf("MIPS16.\n"); break;
        case 0x0366: printf("MIPS with FPU.\n"); break;
        case 0x0466: printf("MIPS16 with FPU.\n"); break;
        case 0x01f0: printf("Power PC, little endian.\n"); break;
        case 0x0162: printf("R3000.\n"); break;
        case 0x0166: printf("R4000MIPS little endian.\n"); break;
        case 0x0168: printf("R10000.\n"); break;
        case 0x01a2: printf("Hitachi SH3.\n"); break;
        case 0x01a6: printf("Hitachi SH4.\n"); break;
        case 0x01c2: printf("THUMB.\n"); break;
        default:     printf("\n"); break;
    }

    printf("Number Of Sections       =  %04d\n", pCoffHeader->usNumSec);
    printf("Time & Date Stamp (After 1970/1/1 0:00:00) =%d\n"  , pCoffHeader->ulTime);
    printf("PointerTo Symbol Table   =0X%04X\n", pCoffHeader->ulSymbolOffset);
    printf("Number Of Symbols        =  %04d\n", pCoffHeader->ulNumSymbol);
    printf("Size   Of Optional Header=  %04d\n", pCoffHeader->usOptHdrSZ);
    printf("Characteristics          =0X%04X\n", pCoffHeader->usFlags);
    if (pCoffHeader->usFlags & 0x0001) {
        printf("Section Flag             =F_RELFLG\n");
    }
    if (pCoffHeader->usFlags & 0x0002) {
        printf("Section Flag             =F_EXEC\n");
    }
    if (pCoffHeader->usFlags & 0x0004) {
        printf("Section Flag             =F_LNNO\n");
    }
    if (pCoffHeader->usFlags & 0x0008) {
        printf("Section Flag             =F_LSYMS\n");
    }
    if (pCoffHeader->usFlags & 0x0010) {
        printf("Section Flag             =F_AGGRESSIVE_WS_TRIM\n");
    }
    if (pCoffHeader->usFlags & 0x0020) {
        printf("Section Flag             =F_LARGE_ADDRESS_AWARE\n");
    }
    if (pCoffHeader->usFlags & 0x0040) {
        printf("Section Flag             =F_16BIT_MACHINE\n");
    }
    if (pCoffHeader->usFlags & 0x0080) {
        printf("Section Flag             =F_BYTES_REVERSED_LO\n");
    }
    if (pCoffHeader->usFlags & 0x0100) {
        printf("Section Flag             =F_AR32WR:F_LITTLE\n");
    }
    if (pCoffHeader->usFlags & 0x0200) {
        printf("Section Flag             =F_BIGDEBUG\n");
    }
    if (pCoffHeader->usFlags & 0x0400) {
        printf("Section Flag             =F_REMOVABLE_RUN_FROM_SWAP\n");
    }
    if (pCoffHeader->usFlags & 0x1000) {
        printf("Section Flag             =F_SYMMERGE:SYSTEM\n");
    }
    if (pCoffHeader->usFlags & 0x2000) {
        printf("Section Flag             =F_DLL\n");
    }
    if (pCoffHeader->usFlags & 0x4000) {
        printf("Section Flag             =F_UP_SYSTEM_ONLY\n");
    }
    if (pCoffHeader->usFlags & 0x6000) {
        printf("Section Flag             =F_BYTES_REVERSED_HI\n");
    }
}

/* Optional Header */
/* Stores additional information about the execution of the file. */
typedef struct _tag_OPTHDR_{
    unsigned short usMagic;       // Magic Number
    unsigned short usVersion;     // Version stamp
    unsigned long ulTextSize;     // Text size in bytes
    unsigned long ulInitDataSZ;   // Initialised data size
    unsigned long ulUninitDataSZ; // Uninitialised data size
    unsigned long ulEntry;        // Entry point
    unsigned long ulTextBase;     // Base of Text used for this file
    unsigned long ulDataBase;     // Base of Data used for this file
} OPTHDR, * POPTHDR;

void display_optional_header(POPTHDR pOpHeader) {
    printf("Magic Number      =%04x\n", pOpHeader->usMagic);
    printf("Version stamp =%04d\n", pOpHeader->usVersion);
    printf("Text size in bytes       =%04d\n", pOpHeader->ulTextSize);
    printf("Initialised data size=%04s\n", pOpHeader->ulInitDataSZ);
    printf("Uninitialised data size     =%04d\n", pOpHeader->ulUninitDataSZ);
    printf("Entry point=%04x\n", pOpHeader->ulEntry);
    printf("Base of Text     =%04x\n", pOpHeader->ulTextBase);
    printf("Base of Data     =%04x\n", pOpHeader->ulDataBase);
}

/* Section Header 40 */
/* Stores information about the different sections defined in the file. */
typedef struct _tag_SECHDR_{
    char cName[8];             // 8Section Name
    //unsigned long ulVSize;     // VirtualSize
    union {
        unsigned long PhysicalAddress; //4 Physical Address
        unsigned long VirtualSize;
     } Misc;
    unsigned long ulVAddr;     // 4VirtualAddress
    unsigned long ulSize;      // 4SizeOfRawData Section Size in Bytes
    unsigned long ulSecOffset; // 4PointerToRawData File offset to the Section data
    unsigned long ulRelOffset; // 4PointerToRelocations File offset to the Relocation table for this Section
    unsigned long ulLNOffset;  // 4PointerToLinenumbers File offset to the Line Number table for this Section
    unsigned short usNumRel;   // 2NumberOfRelocations Number of Relocation table entries
    unsigned short usNumLN;    // 2NumberOfLinenumbers Number of Line Number table entries
    unsigned long ulFlags;     // 4Flags for this section 
} SECHDR, * PSECHDR;

void display_section_header(PSECHDR pSecHeader, unsigned short idx) {
    char section_header_name[9];
    memset(section_header_name, 0, sizeof(section_header_name));
    memcpy(section_header_name, pSecHeader->cName, 8);
    printf("%d Section Header ----------\n", idx);
    printf("Section Name           =%s\n"  , section_header_name);
    printf("Virtual Size           =  %04d\n", pSecHeader->Misc.VirtualSize);
    printf("Virtual Address        =0X%04X\n", pSecHeader->ulVAddr);
    printf("Size    Of RawData     =  %04d\n", pSecHeader->ulSize);
    printf("Pointer To RawData     =0X%04X\n", pSecHeader->ulSecOffset);
    printf("Pointer To Relocations =0X%04X\n", pSecHeader->ulRelOffset);
    printf("Pointer To Linenumbers =0X%04X\n", pSecHeader->ulLNOffset);
    printf("Number  Of Relocations =  %04d\n", pSecHeader->usNumRel);
    printf("Number  Of Linenumbers =  %04d\n", pSecHeader->usNumLN);
    printf("Section Flags          =0X%04X\n", pSecHeader->ulFlags);
    if (pSecHeader->ulFlags & 0x00000020) {
        printf("Section Type           =STYP_TEXT\n");
    }
    if (pSecHeader->ulFlags & 0x00000040) {
        printf("Section Type           =STYP_DATA\n");
    }
    if (pSecHeader->ulFlags & 0x00000080) {
        printf("Section Type           =STYP_BSS\n");
    }
    if (pSecHeader->ulFlags & 0x00000200) {
        printf("Section Type           =STYP_DRECTVE\n");
    }
    if (pSecHeader->ulFlags & 0x01000000) {
        printf("Section Type           =STYP_Section contains extended relocations.\n");
    }
    if (pSecHeader->ulFlags & 0x02000000) {
        printf("Section Type           =STYP_Section can be discarded as needed.\n");
    }
    if (pSecHeader->ulFlags & 0x04000000) {
        printf("Section Type           =STYP_Section cannot be cached\n");
    }
    if (pSecHeader->ulFlags & 0x08000000) {
        printf("Section Type           =STYP_Section is not pageable\n");
    }
    if (pSecHeader->ulFlags & 0x10000000) {
        printf("Section Type           =STYP_Section can be shared in memory.\n");
    }
    if (pSecHeader->ulFlags & 0x20000000) {
        printf("Section Type           =STYP_Section can be executed as code\n");
    }
    if (pSecHeader->ulFlags & 0x40000000) {
        printf("Section Type           =STYP_Section can be read.\n");
    }
    if (pSecHeader->ulFlags & 0x80000000) {
        printf("Section Type           =STYP_Section can be written to.\n");
    }
}

/* Section Relocation Table 10Bytes*/
/* Allows the file to be relocated to any area of memory by providing information on which addresses need to be changed. */
typedef struct _tag_RELOC_{
    unsigned long ulAddr;   // 4Reference Address
    unsigned long ulSymbol; // 4Symbol index
    unsigned short usType;  // 2Type of relocation
} RELOC, * PRELOC;

void display_relocation_entry(PRELOC pRelEntry, unsigned long idx) {
    printf("%d Relocation Entry ----------\n", idx);
    printf("Reference Address    =%04x\n", pRelEntry->ulAddr);
    printf("Symbol    Index      =%04d\n", pRelEntry->ulSymbol);
    printf("Type   Of Relocation =%04x\n", pRelEntry->usType);
    if (pRelEntry->usType == 0x0006) {
        printf("Relocation Type      =RELOC_ADDR32\n");
    }
    if (pRelEntry->usType == 0x0020) {
        printf("Relocation Type      =RELOC_REL32\n");
    }
}

/* Section Line Number Table */
/* Provides debugging information to map code addresses to source file line numbers. */
typedef struct _tag_LINENO_{
    //union {
    //    unsigned long l_symndx;   /* Symbol Index */
    //    unsigned long l_paddr;    /* Physical Address */
    //} l_addr;
    unsigned long ulAddrORSymbol; // 
    unsigned short usLineNo;      // Line Number
} LINENO, * PLINENO;

typedef struct _tag_SYMENT_offset_{
    unsigned long ulZero;   // 4Set to all zeros if the name is longer than eight bytes
    unsigned long ulOffset; // 4Offset into the String Table
} SYMENT_offset;

/* Symbol Table 18Bytes */
/* Stores information on each symbol defined or declared by the code. */
#pragma pack(push)
#pragma pack(1)
typedef struct _tag_SYMENT_{
    union {
        char cName[8]; // 8Symbol Name
        SYMENT_offset e;
    } e;
    unsigned long ulValue;  // 4Value of Symbol 4
             short iSection; // 2Section Number 2
    unsigned short usType;  // 2Symbol Type     2
    unsigned char usClass;  // 1Storage Class   1
    unsigned char usNumAux; // 1Auxiliary Count 1
} SYMENT, * PSYMENT;
#pragma pack(pop)

/*
void test_syment() {
    printf("sizeof(short)=%d.\n", sizeof(short));
    printf("sizeof(signed short)=%d.\n", sizeof(signed short));
    printf("sizeof(SYMENT)=%d.\n", sizeof(SYMENT));
}
*/

void display_symbol_table_entry(PSYMENT pSymTblEnt, unsigned long idx) {
    char symbol_table_entry_name[9];
    printf("%d Symbol Table ----------\n", idx);
    if (pSymTblEnt->e.e.ulZero != 0) {
        memset(symbol_table_entry_name, 0, sizeof(symbol_table_entry_name));
        memcpy(symbol_table_entry_name, pSymTblEnt->e.cName, 8);
        printf("Symbol Name     =%s\n"  , symbol_table_entry_name);
    } else {
        printf("Symbol Name    =%d\n"  , pSymTblEnt->e.e.ulOffset);
    }
    printf("Value of Symbol =%04d\n", pSymTblEnt->ulValue);
    printf("Section Number  =0X%04X\n", pSymTblEnt->iSection);
    if (pSymTblEnt->iSection == -2) {
        printf("Section Number  =N_DEBUG?:A debug? symbol.\n");
    }
    if (pSymTblEnt->iSection == -1) {
        printf("Section Number  =N_CONST:A const symbol.\n");
    }
    if (pSymTblEnt->iSection == 0) {
        printf("Section Number  =N_UNDEF:An undefined external symbol.\n");
    }
    if (pSymTblEnt->iSection == 1) {
        printf("Section Number  =N_ABS:An absolute symbol. This means that the value of the n_value field is an absolute value.\n");
    }
    if (pSymTblEnt->iSection == 2) {
        printf("Section Number  =N_DEBUG:A debugging symbol. In the example below, information about the file has been put into a symbol like this.\n");
    }
    printf("Symbol Type     =  %04d\n", pSymTblEnt->usType);
    printf("Storage Class   =0X%04X\n", pSymTblEnt->usClass);
    if (pSymTblEnt->usClass == 0) {
        printf("Storage Class   =C_NULL\n");
    }
    if (pSymTblEnt->usClass == 1) {
        printf("Storage Class   =C_EXTERNAL\n");
    }
    if (pSymTblEnt->usClass == 2) {
        printf("Storage Class   =C_EXT:EXTERNAL\n");
    }
    if (pSymTblEnt->usClass == 3) {
        printf("Storage Class   =C_STAT:STATIC\n");
    }
    if (pSymTblEnt->usClass == 4) {
        printf("Storage Class   =C_REGISTER\n");
    }
    if (pSymTblEnt->usClass == 8) {
        printf("Storage Class   =C_MEMBER_OF_STRUCT\n");
    }
    if (pSymTblEnt->usClass == 10) {
        printf("Storage Class   =C_STRUCT_TAG\n");
    }
    if (pSymTblEnt->usClass == 11) {
        printf("Storage Class   =C_MEMBER_OF_UNION\n");
    }
    if (pSymTblEnt->usClass == 12) {
        printf("Storage Class   =C_UNION_TAG\n");
    }
    if (pSymTblEnt->usClass == 13) {
        printf("Storage Class   =C_TYPE_DEFINITION\n");
    }
    if (pSymTblEnt->usClass == 101) {
        printf("Storage Class   =C_FUNCTION\n");
    }
    if (pSymTblEnt->usClass == 102) {
        printf("Storage Class   =C_FILE\n");
    }
    printf("Auxiliary Count =0X%04X\n", pSymTblEnt->usNumAux);
}

/* String Table */
/* Stores any Section or Symbol names that are longer than eight characters. */
typedef struct _tag_STRENT_{
    union {
        char    name[8];
        struct {
            unsigned long zeroes;
            unsigned long offset;
        } e;
    } entry;
}STRENT, * PSTRENT;

void display_string_table(char * buf, unsigned long size) {
    unsigned long idx = 1;
    unsigned long cnt = 0;
    printf("%d String Table ----------\n", idx);
    while(cnt < size) {
        //if ((buf + cnt) == '\0') {
            if (buf[cnt] == '\0') {
            printf("\n");
            idx ++;
        } else {
            //printf("%c", (buf + cnt));
            printf("%c", buf[cnt]);
        }
        cnt ++;
    }
}

int main(int argc, char * argv[]) {
//    test_syment();
//    return 0;
    char *in_file_name = NULL;
    FILE * fp_in_file = NULL;
    size_t read_size = 0;
    FILEHDR coff_header;
    OPTHDR  optional_header;
    SECHDR  section_header;
    RELOC   relocation_entry;
    SYMENT  symbol_table_entry;
    unsigned short optional_header_cnt = 0;
    unsigned short section_header_cnd = 0;
    unsigned long first_relocation_offset = 0;
    unsigned long total_relocation_cnt = 0;
    unsigned long relocation_cnt = 0;
    unsigned long symbol_table_cnt = 0;
    unsigned long symbol_table_size = 0;
    unsigned long string_table_offset = 0;
    unsigned long string_table_size = 0;
    unsigned long file_size = 0;

    in_file_name = argv[1];
    fp_in_file = fopen(in_file_name, "rb");
    if (fp_in_file == NULL) {
        printf("%s open is error.\n", in_file_name);
    }
    fseek(fp_in_file, 0, SEEK_END);
    file_size = ftell(fp_in_file);
    printf("%s size is %d\n", in_file_name, file_size);
    fseek(fp_in_file, 0, SEEK_SET);

    /* File Header */
    memset(&coff_header, 0, sizeof(FILEHDR));
    read_size = fread(&coff_header, sizeof(char), sizeof(FILEHDR), fp_in_file);
    if (read_size < sizeof(FILEHDR)) {
        printf("read coff's file header error.%d,%d\n", read_size, sizeof(FILEHDR));
    } else {
        display_coff_header(&coff_header);
    }

    printf("the pos is before Optional Header:0X%06X.\n", ftell(fp_in_file));
    /* Optional Header */
    if (coff_header.usOptHdrSZ > 0) {
        memset(&optional_header, 0, sizeof(OPTHDR));
        read_size = fread(&optional_header, sizeof(char), sizeof(OPTHDR), fp_in_file);
        if (read_size < sizeof(OPTHDR)) {
            printf("read coff's optional header error.%d,%d\n", read_size, sizeof(OPTHDR));
        } else {
            display_optional_header(&optional_header);
        }
    }

    printf("the pos is before Section Header:0X%06X.\n", ftell(fp_in_file));
    /* Section Header */
    while(section_header_cnd < coff_header.usNumSec) {
        section_header_cnd ++;
        memset(&section_header, 0, sizeof(SECHDR));
        read_size = fread(&section_header, sizeof(char), sizeof(SECHDR), fp_in_file);
        if (read_size < sizeof(SECHDR)) {
            printf("read coff's optional header error.%d,%d\n", read_size, sizeof(SECHDR));
        } else {
            display_section_header(&section_header, section_header_cnd);
        }
        if (section_header.ulRelOffset != 0) {
            if (first_relocation_offset == 0) {
                first_relocation_offset = section_header.ulRelOffset;
            }
            total_relocation_cnt  = total_relocation_cnt + section_header.usNumRel;
        }
    }

    printf("the pos is before Raw Data:0X%06X.\n", ftell(fp_in_file));
    /* Raw Data */
    fseek(fp_in_file, first_relocation_offset, SEEK_SET);

    printf("the pos is before Relocation Entries:0X%06X.\n", ftell(fp_in_file));
    /* Relocation Entries */
    while(relocation_cnt < total_relocation_cnt) {
        relocation_cnt ++;
        memset(&relocation_entry, 0, sizeof(RELOC));
        read_size = fread(&relocation_entry, sizeof(char), sizeof(RELOC), fp_in_file);
        if (read_size < sizeof(RELOC)) {
            printf("read coff's optional header error.%d,%d\n", read_size, sizeof(RELOC));
        } else {
            display_relocation_entry(&relocation_entry, relocation_cnt);
        }
    }

    printf("the pos is before Line Number Entries:0X%06X.\n", ftell(fp_in_file));
    /* Line Number Entries */

    fseek(fp_in_file, coff_header.ulSymbolOffset, SEEK_SET);
    printf("the pos is before Symbol Table:0X%06X.\n", ftell(fp_in_file));
    printf("sizeof(SYMENT)=%d.\n", sizeof(SYMENT));
    printf("sizeof(SYMENT_offset)=%d.\n", sizeof(SYMENT_offset));
    /* Symbol Table */
    while(symbol_table_cnt < coff_header.ulNumSymbol) {
        symbol_table_cnt ++;
        memset(&symbol_table_entry, 0, sizeof(SYMENT));
        read_size = fread(&symbol_table_entry, sizeof(char), sizeof(SYMENT), fp_in_file);
        if (read_size < sizeof(SYMENT)) {
            printf("read coff's optional header error.%d,%d\n", read_size, sizeof(SYMENT));
        } else {
            display_symbol_table_entry(&symbol_table_entry, symbol_table_cnt);
        }
    }

    printf("the pos is before String Table:0X%06X.\n", ftell(fp_in_file));
    /* String Table */
    if (coff_header.ulNumSymbol == 0) {
        // The String table contains string names for any sections or symbols that are longer than eight characters.
        symbol_table_size = coff_header.ulNumSymbol * 18;
    } else {
        symbol_table_size = coff_header.ulNumSymbol * sizeof(SYMENT);
    }
    string_table_offset = coff_header.ulSymbolOffset + symbol_table_size;
    printf("String Table Offset is :%X\n", string_table_offset);
    // fseek(fp_in_file, string_table_offset, SEEK_SET);

    read_size = fread(&string_table_size, sizeof(char), 4, fp_in_file);
    if (read_size < 4) {
        printf("read coff's optional header error.%d,%d\n", read_size, 1);
    } else {
        printf("String Table Size is :%d(readed).\n", string_table_size);
    }
    unsigned long cur_pos = ftell(fp_in_file);
    printf("String Table Size is :%d(file).\n", (file_size - cur_pos));

    unsigned long str_tbl_size = file_size - string_table_offset + 1;
    printf("String Table Size is :%d\n", (str_tbl_size - 1));
    str_tbl_size = (file_size - cur_pos) + 1;

    if (str_tbl_size > 0) {
        char * buf = (char *) malloc(sizeof(char) * str_tbl_size);
        if (buf != NULL) {
            memset(buf, 0, sizeof(char) * str_tbl_size);
            read_size = fread(buf, sizeof(char), (str_tbl_size - 1), fp_in_file);
            if (read_size < (str_tbl_size - 1)) {
                printf("read coff's optional header error.%d,%d\n", read_size, str_tbl_size);
            } else {
                display_string_table(buf, str_tbl_size);
            }
            free(buf);
            buf = NULL;
        }
    }

    fclose(fp_in_file);
    fp_in_file = NULL;
    return 0;
}
