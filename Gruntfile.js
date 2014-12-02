module.exports = function (grunt) {

    grunt.initConfig({
        watch: {
            fest: {
                files: ['templates/*.xml'],
                tasks: ['fest'],
                options: {
                    atBegin: true,
                    livereload: true
                }
            },
            sass: {
                files: [
                  'public_html/css/scss/*.scss',
                ],
                tasks: ['sass'],
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
                    src: '*.xml',
                    dest: 'public_html/js/tmpl'
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
            }
        },
        requirejs: {
            compile: {
                options: {
                    baseUrl: 'public_html/js',
                    mainConfigFile: 'public_html/js/config.js',
                    paths: {
                        'requireLib': 'require'
                    },
                    name: 'config',
                    out: 'public_html/build/dist.js',
                    include: ['requireLib'],
                    optimize: 'uglify'
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
    grunt.registerTask('default', ['concurrent']);
    grunt.registerTask('build', ['fest', 'requirejs']);
};

