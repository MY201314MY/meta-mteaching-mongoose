SUMMARY = "C program"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit cmake

SRC_URI = " \
	file://src/main.c \
	file://CMakeLists.txt \
	"

S = "${WORKDIR}"