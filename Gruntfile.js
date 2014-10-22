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
                  'public_html/css/sass/main.sass',
                  'public_html/css/sass/variables.sass',
                  'public_html/css/sass/app.sass',
                  'public_html/css/sass/game.sass',
                  'public_html/public_html/css/sass/fonts.sass'
                ],
                tasks: ['sass'],
                options: {
                    atBegin: true,
                    livereload: true
                }
            },
//            requirejs: {
//                files: [
//                    'js/**/*.js',
//                    'js/*.js'
//                ],
//                tasks: ['requirejs'],
//                options: {
//                    atBegin: true
//                }
//            },
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
                    'public_html/css/main.css': 'public_html/css/sass/main.sass'
                }
            }
        },
        requirejs: {
            compile: {
                options: {
                    baseUrl: 'js',
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
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-fest');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.registerTask('default', ['watch']);
    console.log('azaza');
};
