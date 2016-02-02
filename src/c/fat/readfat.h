#ifndef _READ_FAT_H_
#define _READ_FAT_H_

typedef struct _BIOSParameterBlock {
  unsigned char bpb_BytesPerSector[2];    /* Bytes Per Sector    */
  unsigned char bpb_SectorsPerCluster[1]; /* Sectors Per Cluster */
  unsigned char bpb_ReservedSectorst[2];  /* Reserved Sectors    */
  unsigned char bpb_FatCopies[1];         /* Number of FATs      */
  unsigned char bpb_RootDirEntries[2];    /* Root Entries        */
  unsigned char bpb_NumSectors[2];        /* Small Sectors       */
  unsigned char bpb_MediaType[1];         /* Media Descriptor    */
  unsigned char bpb_SectorsPerFAT[2];     /* Sectors Per FAT     */
  unsigned char bpb_SectorsPerTrack[2];   /* Sectors Per Track   */
  unsigned char bpb_NumberOfHeads[2];     /* Number of Heads     */
  unsigned char bpb_HiddenSectors[4];     /* Hidden Sectors      */
  unsigned char bpb_SectorsBig[4];        /* Large Sectors       */
} BPB, *PBPB;



typedef struct _BootSector {
  unsigned char bs_jmpBoot[3];
  unsigned char bs_OEMName[8];
  BPB bpb;
  unsigned char bs_DrvNum[1];
  unsigned char bs_Reaserved1[1];
  unsigned char bs_Bootsig[1];
  unsigned char bs_VolID[4];
  unsigned char bs_VolLab[11];
  unsigned char bs_FileSysType[8];
  unsigned char bs_Code[448];
  unsigned char bs_EndFlag[2];
} BOOTS, *PBOOTS;


typedef struct _DirectoryEntry {
  /* DOS file name (padded with spaces) */
  unsigned char fileName[8];
/*
 * 0x00 Entry is available and no subsequent entry is in use 
 * 0x05 Initial character is actually 0xE5. 
 * 0x2E 'Dot' entry; either '.' or '..' 
 * 0xE5 Entry has been previously erased and is available. 
 *      File undelete utilities must replace this character with a regular character as part of the undeletion process. 
 */

  /* DOS file extension (padded with spaces) */
  unsigned char fileExtension[3];

  /* File Attributes */
  unsigned char fileAttrube[1];
  /*
   * A SHR I
   * 0 0x01 Read Only R
   * 1 0x02 Hidden 
   * 2 0x04 System 
   * 3 0x08 Volume Label 
   * 4 0x10 Subdirectory 
   * 5 0x20 Archive 
   * 6 0x40 Device (internal use only, never found on disk) 
   * 7 0x80 Unused 
   */

  /* Reserved */
  unsigned char Reserved[1];

  /* Create time */
  unsigned char createTime[1];

  /* Create time 15-11Hours (0-23) 10-5Minutes (0-59) 4-0 Seconds/2 (0-29) */
  unsigned char createTimeHMS[2];

  /* Create date. 15-9Year (0 = 1980, 127 = 2107) 8-5Month (1 = January, 12 = December)4-0Day (1 - 31) */
  unsigned char createDateYMD[2];

  /* Last access date;  */
  unsigned char lastAcDateYMD[2];

  /* EA-Index (used by OS/2 and NT) in FAT12 and FAT16, High 2 bytes of first cluster number in FAT32 */
  unsigned char eaIndex[2];

  /* Last modified time; */
  unsigned char lastModifyTime[2];

  /* Last modified date; */
  unsigned char lastModifyDate[2];

  /* Start of file in clusters in FAT12 and FAT16. 
   * Low 2 bytes of first cluster in FAT32. Entries with the Volume Label flag, 
   * subdirectory ".." pointing to root, 
   * and empty files with size 0 should have first cluster 0. 
   */
  unsigned char startClusters[2];

  /* File size in bytes. Entries with the Volume Label or Subdirectory flag set should have a size of 0. */
  unsigned char fileSize[4];

} DIRENT, *PDIRENT;

#endif
