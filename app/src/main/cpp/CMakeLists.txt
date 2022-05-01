cmake_minimum_required(VERSION 3.14)

project(app CXX)

add_library(app SHARED Calendar.cpp)
target_link_libraries(app -llog)

find_package (chinese-calendar REQUIRED CONFIG)
target_link_libraries(app chinese-calendar::libchinese-calendar.a)
