module.exports = function (grunt) {

    grunt.initConfig({
        watch: {
            fest: {
                files: ['templates/*.xml', 'templates/**/*.xml'],
                tasks: ['fest'],
                options: {
                    atBegin: true,
                    livereload: true
                }
            },
            sass: {
                files: [
                  'public_html/css/scss/*.scss',
                  'public_html/css/scss/**/*.scss'
                ],
                tasks: ['sass:dist'],
                options: {
                    atBegin: true,
                    livereload: true
                }
            },
            server: {
                files: [
                    'public_html/js/**/*.js',
                    'public_html/css/**/*.css'
                ],
                options: {
                    interrupt: true,
                    livereload: true
                }
            }
        },
        fest: {
            templates: {
                files: [{
                    expand: true,
                    cwd: 'templates',
                    src: 'all.xml',
                    dest: 'public_html/js/tmpl'
                },
                {
                    expand: true,
                    cwd: 'templates/joystick',
                    src: '*.xml',
                    dest: 'public_html/js/tmpl/joystick'
                }],
                options: {
                    template: function (data) {
                        return grunt.template.process(
                            'define(function () { return <%= contents %> ; });',
                            {data: data}
                        );
                    }
                }
            }
        },
        sass: {
            dist: {
                files: {
                    'public_html/css/main.css': 'public_html/css/scss/main.scss'
                }
            },
            compress: {
                options: {
                    style: 'compressed'
                },
                files: {
                    'public_html/css/main.min.css': 'public_html/css/scss/main.scss'
                }
            }
        },
        requirejs: {
            compile: {
                options: {
                    almond: true,
                    baseUrl: 'public_html/js',
                    mainConfigFile: 'public_html/js/config.js',
                    name: 'config',
                    out: 'public_html/build/dist.js',
                    optimize: 'none'
                }
            }
        },
        concat: {
            build: {
                separator: ';\n',
                src: [
                    'public_html/js/lib/almond/almond.js',
                    'public_html/build/dist.js'
                ],
                dest: 'public_html/build/build.js'
            }
        },
        uglify: {
            build: {
                files: {
                    'public_html/build/build.min.js': ['public_html/build/build.js']
                }
            }
        },
        shell: {
            server: {
                command: 'java -cp target/uno-1.0-jar-with-dependencies.jar main.Main'
            },
            compile: {
                command: 'mvn compile assembly:single'
            }
        },
        concurrent: {
            target: ['watch', 'shell:server'],
            options: {
                logCurrentOutput: true
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-fest');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-concurrent');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.registerTask('default', ['watch']);
    grunt.registerTask('build', ['fest', 'requirejs', 'concat', 'uglify', 'sass:compress']);
};
