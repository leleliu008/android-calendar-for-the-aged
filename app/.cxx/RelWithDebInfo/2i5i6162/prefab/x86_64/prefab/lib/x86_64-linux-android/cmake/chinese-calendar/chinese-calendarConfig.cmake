if(NOT TARGET chinese-calendar::libchinese-calendar.a)
add_library(chinese-calendar::libchinese-calendar.a STATIC IMPORTED)
set_target_properties(chinese-calendar::libchinese-calendar.a PROPERTIES
    IMPORTED_LOCATION "/home/leleliu008/.gradle/caches/transforms-3/85b56797aae76cf43b611d94742a2046/transformed/chinese-calendar-2022.04.24/prefab/modules/libchinese-calendar.a/libs/android.x86_64/libchinese-calendar.a"
    INTERFACE_INCLUDE_DIRECTORIES "/home/leleliu008/.gradle/caches/transforms-3/85b56797aae76cf43b611d94742a2046/transformed/chinese-calendar-2022.04.24/prefab/modules/libchinese-calendar.a/libs/android.x86_64/include"
    INTERFACE_LINK_LIBRARIES ""
)
endif()

