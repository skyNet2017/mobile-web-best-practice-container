cmake_minimum_required(VERSION 3.4.1)

project("bspatch")

add_library(
        mybspatch

        SHARED


        bspatch.c
        bs.c
        bzip2/blocksort.c
        bzip2/bzip2.c
        bzip2/bzip2recover.c
        bzip2/bzlib.c
        bzip2/compress.c
        bzip2/crctable.c
        bzip2/decompress.c
        bzip2/huffman.c
        bzip2/randtable.c


)


find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_link_libraries( # Specifies the target library.
        mybspatch

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})