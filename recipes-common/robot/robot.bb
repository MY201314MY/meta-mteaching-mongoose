SUMMARY = "helloworld program"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = " \
    git://github.com/MY201314MY/robot.git;branch=master;protocol=https \
"

# git commit id
SRCREV = "2ff36cc84347eddb1afb272bbf0fd91b469d8e38"

S = "${WORKDIR}/git"

inherit cmake

do_compile () {
	cmake ${S}
	make
}

do_configure () {
	echo configure
}

do_package () {
	echo package
}

do_package_write_rpm () {
	echo rpm
}