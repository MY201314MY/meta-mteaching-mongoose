###### source

``` shell
pi@inspiron:~/Documents/yocto/poky$ source oe-init-build-env 

### Shell environment set up for builds. ###

You can now run 'bitbake <target>'

Common targets are:
    core-image-minimal
    core-image-full-cmdline
    core-image-sato
    core-image-weston
    meta-toolchain
    meta-ide-support

You can also run generated qemu images with a command like 'runqemu qemux86'

Other commonly useful commands are:
 - 'devtool' and 'recipetool' handle common recipe tasks
 - 'bitbake-layers' handles common layer tasks
 - 'oe-pkgdata-util' handles common target package tasks

```

###### bitbake-layers

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake-layers --help
NOTE: Starting bitbake server...
usage: bitbake-layers [-d] [-q] [-F] [--color COLOR] [-h] <subcommand> ...

BitBake layers utility

options:
  -d, hdebug           Enable debug output
  -q, --quiet           Print only errors
  -F, --force           Force add without recipe parse verification
  --color COLOR         Colorize output (where COLOR is auto, always, never)
  -h, --help            show this help message and exit

subcommands:
  <subcommand>
    add-layer           Add one or more layers to bblayers.conf.
    remove-layer        Remove one or more layers from bblayers.conf.
    flatten             flatten layer configuration into a separate output directory.
    show-layers         show current configured layers.
    show-overlayed      list overlayed recipes (where the same recipe exists in another layer)
    show-recipes        list available recipes, showing the layer they are provided by
    show-appends        list bbappend files and recipe files they apply to
    show-cross-depends  Show dependencies between recipes that cross layer boundaries.
    layerindex-fetch    Fetches a layer from a layer index along with its dependent layers, and adds them to conf/bblayers.conf.
    layerindex-show-depends
                        Find layer dependencies from layer index.
    create-layer        Create a basic layer

Use bitbake-layers <subcommand> --help to get help on a specific command
```



```shell
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/pi/Documents/yocto/poky/meta        5
meta-poky             /home/pi/Documents/yocto/poky/meta-poky   5
meta-yocto-bsp        /home/pi/Documents/yocto/poky/meta-yocto-bsp  5
workspace             /home/pi/Documents/yocto/poky/build/workspace  99
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/conf$ cat bblayers.conf 
# POKY_BBLAYERS_CONF_VERSION is increased each time build/conf/bblayers.conf
# changes incompatibly
POKY_BBLAYERS_CONF_VERSION = "2"

BBPATH = "${TOPDIR}"
BBFILES ?= ""

BBLAYERS ?= " \
  /home/pi/Documents/yocto/poky/meta \
  /home/pi/Documents/yocto/poky/meta-poky \
  /home/pi/Documents/yocto/poky/meta-yocto-bsp \
  /home/pi/Documents/yocto/poky/build/workspace \
  "
```



```shell
pi@inspiron:~/Documents/yocto$ bitbake-layers show-appends
NOTE: Starting bitbake server...
Loading cache: 100% |###############################################################################################################################################################################################################################################| Time: 0:00:00
Loaded 1644 entries from dependency cache.
=== Appended recipes ===
busybox_1.35.0.bb:
  /home/pi/Documents/yocto/poky/meta-poky/recipes-core/busybox/busybox_%.bbappend
formfactor_0.0.bb:
  /home/pi/Documents/yocto/poky/meta-yocto-bsp/recipes-bsp/formfactor/formfactor_0.0.bbappend
linux-yocto_5.15.bb:
  /home/pi/Documents/yocto/poky/meta-yocto-bsp/recipes-kernel/linux/linux-yocto_5.15.bbappend
linux-yocto_5.10.bb:
  /home/pi/Documents/yocto/poky/meta-yocto-bsp/recipes-kernel/linux/linux-yocto_5.10.bbappend
psplash_git.bb:
  /home/pi/Documents/yocto/poky/meta-poky/recipes-core/psplash/psplash_git.bbappend
xserver-xf86-config_0.1.bb:
  /home/pi/Documents/yocto/poky/meta-yocto-bsp/recipes-graphics/xorg-xserver/xserver-xf86-config_0.1.bbappend
linux-yocto-dev.bb (skipped):
  /home/pi/Documents/yocto/poky/meta-yocto-bsp/recipes-kernel/linux/linux-yocto-dev.bbappend
linux-yocto_5.15.bb (skipped):
  /home/pi/Documents/yocto/poky/meta-yocto-bsp/recipes-kernel/linux/linux-yocto_5.15.bbappend
```

##### bitbake

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake --help
Usage: bitbake [options] [recipename/target recipe:do_task ...]

    Executes the specified task (default is 'build') for a given set of target recipes (.bb files).
    It is assumed there is a conf/bblayers.conf available in cwd or in BBPATH which
    will provide the layer, BBFILES and other configuration information.

Options:
  --version             show program's version number and exit
  -h, --help            show this help message and exit
  -b BUILDFILE, --buildfile=BUILDFILE
                        Execute tasks from a specific .bb recipe directly.
                        WARNING: Does not handle any dependencies from other
                        recipes.
  -k, --continue        Continue as much as possible after an error. While the
                        target that failed and anything depending on it cannot
                        be built, as much as possible will be built before
                        stopping.
  -f, --force           Force the specified targets/task to run (invalidating
                        any existing stamp file).
  -c CMD, --cmd=CMD     Specify the task to execute. The exact options
                        available depend on the metadata. Some examples might
                        be 'compile' or 'populate_sysroot' or 'listtasks' may
                        give a list of the tasks available.
  -C INVALIDATE_STAMP, --clear-stamp=INVALIDATE_STAMP
                        Invalidate the stamp for the specified task such as
                        'compile' and then run the default task for the
                        specified target(s).
  -r PREFILE, --read=PREFILE
                        Read the specified file before bitbake.conf.
  -R POSTFILE, --postread=POSTFILE
                        Read the specified file after bitbake.conf.
  -v, --verbose         Enable tracing of shell tasks (with 'set -x'). Also
                        print bb.note(...) messages to stdout (in addition to
                        writing them to ${T}/log.do_<task>).
  -D, --debug           Increase the debug level. You can specify this more
                        than once. -D sets the debug level to 1, where only
                        bb.debug(1, ...) messages are printed to stdout; -DD
                        sets the debug level to 2, where both bb.debug(1, ...)
                        and bb.debug(2, ...) messages are printed; etc.
                        Without -D, no debug messages are printed. Note that
                        -D only affects output to stdout. All debug messages
                        are written to ${T}/log.do_taskname, regardless of the
                        debug level.
  -q, --quiet           Output less log message data to the terminal. You can
                        specify this more than once.
  -n, --dry-run         Don't execute, just go through the motions.
  -S SIGNATURE_HANDLER, --dump-signatures=SIGNATURE_HANDLER
                        Dump out the signature construction information, with
                        no task execution. The SIGNATURE_HANDLER parameter is
                        passed to the handler. Two common values are none and
                        printdiff but the handler may define more/less. none
                        means only dump the signature, printdiff means compare
                        the dumped signature with the cached one.
  -p, --parse-only      Quit after parsing the BB recipes.
  -s, --show-versions   Show current and preferred versions of all recipes.
  -e, --environment     Show the global or per-recipe environment complete
                        with information about where variables were
                        set/changed.
  -g, --graphviz        Save dependency tree information for the specified
                        targets in the dot syntax.
  -I EXTRA_ASSUME_PROVIDED, --ignore-deps=EXTRA_ASSUME_PROVIDED
                        Assume these dependencies don't exist and are already
                        provided (equivalent to ASSUME_PROVIDED). Useful to
                        make dependency graphs more appealing
  -l DEBUG_DOMAINS, --log-domains=DEBUG_DOMAINS
                        Show debug logging for the specified logging domains
  -P, --profile         Profile the command and save reports.
  -u UI, --ui=UI        The user interface to use (knotty, ncurses, taskexp or
                        teamcity - default knotty).
  --token=XMLRPCTOKEN   Specify the connection token to be used when
                        connecting to a remote server.
  --revisions-changed   Set the exit code depending on whether upstream
                        floating revisions have changed or not.
  --server-only         Run bitbake without a UI, only starting a server
                        (cooker) process.
  -B BIND, --bind=BIND  The name/address for the bitbake xmlrpc server to bind
                        to.
  -T SERVER_TIMEOUT, --idle-timeout=SERVER_TIMEOUT
                        Set timeout to unload bitbake server due to
                        inactivity, set to -1 means no unload, default:
                        Environment variable BB_SERVER_TIMEOUT.
  --no-setscene         Do not run any setscene tasks. sstate will be ignored
                        and everything needed, built.
  --skip-setscene       Skip setscene tasks if they would be executed. Tasks
                        previously restored from sstate will be kept, unlike
                        --no-setscene
  --setscene-only       Only run setscene tasks, don't run any real tasks.
  --remote-server=REMOTE_SERVER
                        Connect to the specified server.
  -m, --kill-server     Terminate any running bitbake server.
  --observe-only        Connect to a server as an observing-only client.
  --status-only         Check the status of the remote bitbake server.
  -w WRITEEVENTLOG, --write-log=WRITEEVENTLOG
                        Writes the event log of the build to a bitbake event
                        json file. Use '' (empty string) to assign the name
                        automatically.
  --runall=RUNALL       Run the specified task for any recipe in the taskgraph
                        of the specified target (even if it wouldn't otherwise
                        have run).
  --runonly=RUNONLY     Run only the specified task within the taskgraph of
                        the specified targets (and any task dependencies those
                        tasks may have).
```

#### ambiguous

##### ***linux-yocto***

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -s | grep linux
binutils-crosssdk-x86_64-pokysdk-linux                  :2.38-r0                                                    
cryptodev-linux                                      :1.12-r0                                                    
cryptodev-linux-native                               :1.12-r0                                                    
gcc-crosssdk-x86_64-pokysdk-linux                  :11.4.0-r0                                                    
go-crosssdk-x86_64-pokysdk-linux                  :1.17.13-r0                                                    
linux-firmware                                  1:20230804-r0                                                    
linux-libc-headers                                   :5.16-r0                                                    
linux-yocto                         :5.15.124+gitAUTOINC+f484a7f175_1c09be01f4-r0                                                 
nativesdk-cryptodev-linux                            :1.12-r0                                                    
nativesdk-linux-libc-headers                         :5.16-r0                                                    
nativesdk-syslinux                              :6.04-pre2-r1                                                    
nativesdk-util-linux                               :2.37.4-r0                                                    
nativesdk-util-linux-libuuid                       :2.37.4-r0                                                    
syslinux                                        :6.04-pre2-r1                                                    
syslinux-native                                 :6.04-pre2-r1                                                    
util-linux                                         :2.37.4-r0                                                    
util-linux-libuuid                                 :2.37.4-r0                                                    
util-linux-libuuid-native                          :2.37.4-r0                                                    
util-linux-native                                  :2.37.4-r0 
```



##### recipe

###### env

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^S="
S="/home/pi/Documents/yocto/poky/build/tmp/work-shared/qemux86-64/kernel-source"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^WORKDIR="
WORKDIR="/home/pi/Documents/yocto/poky/build/tmp/work/qemux86_64-poky-linux/linux-yocto/5.15.124+gitAUTOINC+f484a7f175_1c09be01f4-r0"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^B="
B="/home/pi/Documents/yocto/poky/build/tmp/work/qemux86_64-poky-linux/linux-yocto/5.15.124+gitAUTOINC+f484a7f175_1c09be01f4-r0/linux-qemux86_64-standard-build"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^FILE="
FILE="/home/pi/Documents/yocto/poky/meta/recipes-kernel/linux/linux-yocto_5.15.bb"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^PACKAGES="
PACKAGES="kernel kernel-base kernel-vmlinux kernel-image kernel-dev kernel-modules kernel-dbg kernel-image-bzimage kernel-devicetree"
```



```shell
PACKAGES="kernel kernel-base kernel-vmlinux kernel-image kernel-dev kernel-modules kernel-dbg kernel-image-bzimage kernel-devicetree"
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^PV="
PV="5.15.124+gitAUTOINC+f484a7f175_1c09be01f4"
```

###### log

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e linux-yocto | grep "^T="
T="/home/pi/Documents/yocto/poky/build/tmp/work/qemux86_64-poky-linux/linux-yocto/5.15.124+gitAUTOINC+f484a7f175_1c09be01f4-r0/temp"
```



###### another style

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake-getvar -r linux-yocto S
#
# $S [3 operations]
#   set /home/pi/Documents/yocto/poky/meta/conf/bitbake.conf:405
#     "${WORKDIR}/${BP}"
#   set /home/pi/Documents/yocto/poky/meta/conf/documentation.conf:368
#     [doc] "The location in the Build Directory where unpacked package source code resides."
#   set /home/pi/Documents/yocto/poky/meta/classes/kernel.bbclass:20
#     "${STAGING_KERNEL_DIR}"
# pre-expansion value:
#   "${STAGING_KERNEL_DIR}"
S="/home/pi/Documents/yocto/poky/build/tmp/work-shared/qemux86-64/kernel-source"
```



###### task

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake linux-yocto -c listtasks
Loading cache: 100% |###############################################################################################################################################################################################################################################| Time: 0:00:00
Loaded 1644 entries from dependency cache.
NOTE: Resolving any missing task queue dependencies

Build Configuration:
BB_VERSION           = "2.0.0"
BUILD_SYS            = "x86_64-linux"
NATIVELSBSTRING      = "universal"
TARGET_SYS           = "x86_64-poky-linux"
MACHINE              = "qemux86-64"
DISTRO               = "poky"
DISTRO_VERSION       = "4.0.15"
TUNE_FEATURES        = "m64 core2"
TARGET_FPU           = ""
meta                 
meta-poky            
meta-yocto-bsp       
workspace            = "dev-yocto-1.0.0:3ef22a75a3d97cd68ebaad476882937baedd9d25"

Initialising tasks: 100% |##########################################################################################################################################################################################################################################| Time: 0:00:00
Sstate summary: Wanted 0 Local 0 Mirrors 0 Missed 0 Current 0 (0% match, 0% complete)
NOTE: No setscene tasks
NOTE: Executing Tasks
do_build                              Default task for a recipe - depends on all other normal tasks required to 'build' a recipe
do_bundle_initramfs                   Combines an initial ramdisk image and kernel together to form a single image
do_checkuri                           Validates the SRC_URI value
do_clean                              Removes all output files for a target
do_cleanall                           Removes all output files, shared state cache, and downloaded source files for a target
do_cleansstate                        Removes all output files and shared state cache for a target
do_compile                            Compiles the source in the compilation directory
do_compile_kernelmodules              Compiles loadable modules for the Linux kernel
do_config_analysis                    
do_configure                          Configures the source by enabling and disabling any build-time and configuration options for the software being built
do_deploy                             Writes deployable output files to the deploy directory
do_deploy_setscene                    Writes deployable output files to the deploy directory (setscene version)
do_deploy_source_date_epoch           
do_deploy_source_date_epoch_setscene   (setscene version)
do_devshell                           Starts a shell with the environment set up for development/debugging
do_diffconfig                         Compares the old and new config files after running do_menuconfig for the kernel
do_fetch                              Fetches the source code
do_install                            Copies files from the compilation directory to a holding area
do_kernel_checkout                    Checks out source/meta branches for a linux-yocto style kernel
do_kernel_configcheck                 Validates the kernel configuration for a linux-yocto style kernel
do_kernel_configme                    Assembles the kernel configuration for a linux-yocto style kernel
do_kernel_link_images                 Creates a symbolic link in arch/$arch/boot for vmlinux and vmlinuz kernel images
do_kernel_metadata                    
do_kernel_version_sanity_check        
do_listtasks                          Lists all defined tasks for a target
do_menuconfig                         Runs 'make menuconfig' for the kernel
do_package                            Analyzes the content of the holding area and splits it into subsets based on available packages and files
do_package_qa                         Runs QA checks on packaged files
do_package_qa_setscene                Runs QA checks on packaged files (setscene version)
do_package_setscene                   Analyzes the content of the holding area and splits it into subsets based on available packages and files (setscene version)
do_package_write_rpm                  Creates the actual RPM packages and places them in the Package Feed area
do_package_write_rpm_setscene         Creates the actual RPM packages and places them in the Package Feed area (setscene version)
do_packagedata                        Creates package metadata used by the build system to generate the final packages
do_packagedata_setscene               Creates package metadata used by the build system to generate the final packages (setscene version)
do_patch                              Locates patch files and applies them to the source code
do_populate_lic                       Writes license information for the recipe that is collected later when the image is constructed
do_populate_lic_setscene              Writes license information for the recipe that is collected later when the image is constructed (setscene version)
do_populate_sysroot                   Copies a subset of files installed by do_install into the sysroot in order to make them available to other recipes
do_populate_sysroot_setscene          Copies a subset of files installed by do_install into the sysroot in order to make them available to other recipes (setscene version)
do_prepare_recipe_sysroot             
do_pydevshell                         Starts an interactive Python shell for development/debugging
do_savedefconfig                      Creates a minimal Linux kernel configuration file
do_shared_workdir                     
do_shared_workdir_setscene             (setscene version)
do_sizecheck                          Checks the size of the kernel image against KERNEL_IMAGE_MAXSIZE (if set)
do_strip                              Strips unneeded sections out of the Linux kernel image
do_symlink_kernsrc                    
do_transform_kernel                   
do_unpack                             Unpacks the source code into a working directory
do_validate_branches                  Ensures that the source/meta branches are on the locations specified by their SRCREV values for a linux-yocto style kernel
NOTE: Tasks Summary: Attempted 1 tasks of which 0 didn't need to be rerun and all succeeded.
```

# helloworld

## prepare

```shell
pi@inspiron:~/Documents/yocto$ bitbake-layers create-layer meta-mteaching-mongoose
NOTE: Starting bitbake server...
Add your new layer with 'bitbake-layers add-layer meta-mteaching-mongoose'
pi@inspiron:~/Documents/yocto$ bitbake-layers show-layers
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/pi/Documents/yocto/poky/meta        5
meta-poky             /home/pi/Documents/yocto/poky/meta-poky   5
meta-yocto-bsp        /home/pi/Documents/yocto/poky/meta-yocto-bsp  5
workspace             /home/pi/Documents/yocto/poky/build/workspace  99
```



```shell
pi@inspiron:~/Documents/yocto/meta-mteaching-mongoose$ tree
.
├── conf
│   └── layer.conf
├── COPYING.MIT
├── README
└── recipes-example
    └── example
        └── example_0.1.bb

3 directories, 4 files
```



```shell
pi@inspiron:~/Documents/yocto$ bitbake-layers add-layer meta-mteaching-mongoose
NOTE: Starting bitbake server...
Unable to find bblayers.conf

pi@inspiron:~/Documents/yocto$ cd poky/build/
pi@inspiron:~/Documents/yocto/poky/build$ bitbake-layers add-layer ../../meta-mteaching-mongoose
NOTE: Starting bitbake server...
pi@inspiron:~/Documents/yocto/poky/build$ bitbake-layers show-layers
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/pi/Documents/yocto/poky/meta        5
meta-poky             /home/pi/Documents/yocto/poky/meta-poky   5
meta-yocto-bsp        /home/pi/Documents/yocto/poky/meta-yocto-bsp  5
workspace             /home/pi/Documents/yocto/poky/build/workspace  99
meta-mteaching-mongoose  /home/pi/Documents/yocto/meta-mteaching-mongoose  6
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -s | grep example
example                                               :0.1-r0                                                    
gst-examples                                       :1.18.6-r0                                                    
pi@inspiron:~/Documents/yocto/poky/build$ bitbake example
Loading cache: 100% |###############################################################################################################################################################################################################################################| Time: 0:00:00
Loaded 1646 entries from dependency cache.
NOTE: Resolving any missing task queue dependencies

Build Configuration:
BB_VERSION           = "2.0.0"
BUILD_SYS            = "x86_64-linux"
NATIVELSBSTRING      = "universal"
TARGET_SYS           = "x86_64-poky-linux"
MACHINE              = "qemux86-64"
DISTRO               = "poky"
DISTRO_VERSION       = "4.0.15"
TUNE_FEATURES        = "m64 core2"
TARGET_FPU           = ""
meta                 
meta-poky            
meta-yocto-bsp       
workspace            = "dev-yocto-1.0.0:3ef22a75a3d97cd68ebaad476882937baedd9d25"
meta-mteaching-mongoose = "master:f4aa2460d40d2139725833dc5a9a479ff0cda804"

Initialising tasks: 100% |##########################################################################################################################################################################################################################################| Time: 0:00:00
Sstate summary: Wanted 7 Local 0 Mirrors 0 Missed 7 Current 147 (0% match, 95% complete)
NOTE: Executing Tasks
***********************************************
*                                             *
*  Example recipe created by bitbake-layers   *
*                                             *
***********************************************
NOTE: Tasks Summary: Attempted 639 tasks of which 623 didn't need to be rerun and all succeeded.
pi@inspiron:~/Documents/yocto/poky/build$ bitbake example
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake-layers show-recipes | grep helloworld
go-helloworld:
helloworld:
```



```shell
pi@inspiron:~/Documents/yocto/meta-mteaching-mongoose$ tree
.
├── conf
│   └── layer.conf
├── COPYING.MIT
├── README
├── recipes-common
│   └── helloworld
│       ├── files
│       │   └── helloworld.c
│       └── helloworld.bb
└── recipes-example
    └── example
        └── example_0.1.bb
```

## source

**helloworld.bb**

```bitbake
SUMMARY = "helloworld program"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

SRC_URI = "file://helloworld.c"

S = "${WORKDIR}/build"

do_compile () {
	${CC} ${CFLAGS} ${LDFLAGS} ${WORKDIR}/helloworld.c -o ${S}/helloworld
}
```

**helloworld.c**

```c
#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv)
{
	printf("hello,world.");

	return 0;
}
```



## build

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake helloworld
Loading cache: 100% |###############################################################################################################################################################################################################################################| Time: 0:00:00
Loaded 1646 entries from dependency cache.
NOTE: Resolving any missing task queue dependencies

Build Configuration:
BB_VERSION           = "2.0.0"
BUILD_SYS            = "x86_64-linux"
NATIVELSBSTRING      = "universal"
TARGET_SYS           = "x86_64-poky-linux"
MACHINE              = "qemux86-64"
DISTRO               = "poky"
DISTRO_VERSION       = "4.0.15"
TUNE_FEATURES        = "m64 core2"
TARGET_FPU           = ""
meta                 
meta-poky            
meta-yocto-bsp       
workspace            = "dev-yocto-1.0.0:3ef22a75a3d97cd68ebaad476882937baedd9d25"
meta-mteaching-mongoose = "master:f4aa2460d40d2139725833dc5a9a479ff0cda804"

Initialising tasks: 100% |##########################################################################################################################################################################################################################################| Time: 0:00:00
Sstate summary: Wanted 0 Local 0 Mirrors 0 Missed 0 Current 154 (0% match, 100% complete)
NOTE: Executing Tasks
NOTE: Tasks Summary: Attempted 638 tasks of which 638 didn't need to be rerun and all succeeded.
```



```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake -e helloworld | grep "^B="
B="/home/pi/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/helloworld/1.0-r0/build"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/helloworld/1.0-r0/build$ ls -l
total 24
-rwxr-xr-x 1 pi pi 21792  1月  9 13:38 helloworld
```

# cmake

## reference URL

https://blog.csdn.net/zz2633105/article/details/122867225

## source

```shell
pi@inspiron:~/Documents/yocto/meta-mteaching-mongoose/recipes-common/california$ tree
.
├── california.bb
└── files
    ├── CMakeLists.txt
    └── src
        └── main.c

2 directories, 3 files
```

**california.bb**

```bitbake
SUMMARY = "C program"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

inherit cmake

SRC_URI = " \
	file://src/main.c \
	file://CMakeLists.txt \
	"

S = "${WORKDIR}"
```

**main.c**

```c
#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv)
{
	printf("california.\r\n");

	return 0;
}
```

**CMakeLists.txt**

```cmake
cmake_minimum_required(VERSION 3.12.4)

project(california VERSION 0.1.0)

add_executable(california ./src/main.c)

install(TARGETS california DESTINATION ${CMAKE_INSTALL_PREFIX}/sbin)
```

## build

```shell
pi@inspiron:~/Documents/yocto/poky/build$ bitbake california
Loading cache: 100% |###############################################################################################################################################################################################################################################| Time: 0:00:00
Loaded 1647 entries from dependency cache.
NOTE: Resolving any missing task queue dependencies

Build Configuration:
BB_VERSION           = "2.0.0"
BUILD_SYS            = "x86_64-linux"
NATIVELSBSTRING      = "universal"
TARGET_SYS           = "x86_64-poky-linux"
MACHINE              = "qemux86-64"
DISTRO               = "poky"
DISTRO_VERSION       = "4.0.15"
TUNE_FEATURES        = "m64 core2"
TARGET_FPU           = ""
meta                 
meta-poky            
meta-yocto-bsp       
workspace            = "dev-yocto-1.0.0:3ef22a75a3d97cd68ebaad476882937baedd9d25"
meta-mteaching-mongoose = "master:11b1d58a036e1212d2df4df312d84cf620192ac1"

Initialising tasks: 100% |##########################################################################################################################################################################################################################################| Time: 0:00:00
Sstate summary: Wanted 7 Local 0 Mirrors 0 Missed 7 Current 153 (0% match, 95% complete)
Removing 2 stale sstate objects for arch core2-64: 100% |###########################################################################################################################################################################################################| Time: 0:00:00
NOTE: Executing Tasks
NOTE: Tasks Summary: Attempted 666 tasks of which 650 didn't need to be rerun and all succeeded.
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0/build$ bitbake -e california | grep "^B="
B="/home/pi/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0/build"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0/build$ ls -l
total 92
-rw-r--r-- 1 pi pi 24844  1月  9 14:23 build.ninja
-rwxr-xr-x 1 pi pi 21864  1月  9 14:23 california
-rw-r--r-- 1 pi pi 25543  1月  9 14:23 CMakeCache.txt
drwxr-xr-x 5 pi pi  4096  1月  9 14:23 CMakeFiles
-rw-r--r-- 1 pi pi  3140  1月  9 14:23 cmake_install.cmake
-rw-r--r-- 1 pi pi    20  1月  9 14:23 install_manifest.txt
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0$ bitbake -e california | grep "^S="
S="/home/pi/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0"
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0$ find . | grep main.c
./packages-split/california-src/usr/src/debug/california/1.0-r0/src/main.c
./src/main.c
./build/CMakeFiles/california.dir/src/main.c.o
./recipe-sysroot-native/usr/share/cmake-3.22/Modules/FortranCInterface/Verify/main.c
./recipe-sysroot-native/usr/share/cmake-3.22/Modules/CheckIPOSupported/main.cpp
./recipe-sysroot-native/usr/share/cmake-3.22/Modules/CheckIPOSupported/main.c
./package/usr/src/debug/california/1.0-r0/src/main.c
```



```shell
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0$ ls -l src
total 4
-rw-r--r-- 3 pi pi 116  4月  6  2011 main.c
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0$ cat src/main.c
#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv)
{
	printf("california.\r\n");

	return 0;
}
pi@inspiron:~/Documents/yocto/poky/build/tmp/work/core2-64-poky-linux/california/1.0-r0$ cat CMakeLists.txt
cmake_minimum_required(VERSION 3.12.4)

project(california VERSION 0.1.0)

add_executable(california ./src/main.c)

install(TARGETS california DESTINATION ${CMAKE_INSTALL_PREFIX}/sbin)
```



