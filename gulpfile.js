var gulp = require('gulp'),
    livereload = require('gulp-livereload'),
    sass = require('gulp-sass'),
    map   = require('map-stream'),
    sourcemaps = require('gulp-sourcemaps'),
    watch = require('gulp-watch'),
    fest = require('fest');


var festTask = function () {
    var makefest = function (file, cb) {
        var src = fest.compile(file.path, {beautify: false});
        handleOutput('define(function () { return ' + src + '; });', file, cb);
    };

    function handleOutput(output, file, cb) {
        file.path = file.path.replace('.xml', '.js');
        file.contents = new Buffer(output);
        cb(null, file);
    }

    return map(makefest);
};

gulp.task('sass', function () {
    gulp.src('public_html/css/scss/main.scss')
    .pipe(sass())
    .pipe(sourcemaps.write())
    .pipe(gulp.dest('public_html/css/main.css'));
});

gulp.task('fest', function () {
    gulp.src('templates/**/*.xml')
    .pipe(festTask())
    .pipe(gulp.dest('public_html/js/tmpl/'));
});

gulp.task('watch', function () {
    gulp.watch('public_html/css/**/*.scss', ['sass']);
    gulp.watch('templates/**/*.xml', ['fest']);
});

gulp.task('default', ['watch'], function () {
    console.log('Running tasks...');
});
