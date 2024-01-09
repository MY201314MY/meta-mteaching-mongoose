SUMMARY = "helloworld program"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "file://helloworld.c"

S = "${WORKDIR}/build"

do_compile () {
	${CC} ${CFLAGS} ${LDFLAGS} ${WORKDIR}/helloworld.c -o ${S}/helloworld
}