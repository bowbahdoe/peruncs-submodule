# Shows this message
help:
    @just --list

# Downloads external dependencies
install:
    rm -rf libs
    jresolve --output-directory libs @libs.txt

# Cleans up any build artifacts
clean:
    rm -rf build
build:
    rm -rf build/javac
    javac \
      -d build/javac \
      --module-path libs \
      --module-source-path "./*/src:peruncs-submodule/*/src" \
      --module peruncs.utilities,peruncs.helidon,peruncs.webassets \
      --enable-preview \
      --release 22


    jar --create \
        --file build/jar/peruncs.utilities.jar \
        -C build/javac/peruncs.utilities .

    jar --create \
        --file build/jar/peruncs.helidon.jar \
        -C build/javac/peruncs.helidon .

    jar --create \
        --file build/jar/peruncs.webassets.jar \
        -C build/javac/peruncs.webassets . \
        -C peruncs.webassets/res .
