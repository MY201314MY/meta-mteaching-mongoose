cmake_minimum_required(VERSION 3.12.4)

project(california VERSION 0.1.0)

add_executable(california ./src/main.c)

install(TARGETS california DESTINATION ${CMAKE_INSTALL_PREFIX}/sbin)