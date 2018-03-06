var gulp = require('gulp');
var concat = require('gulp-concat');

gulp.task('pack-js', function () {
    return gulp.src([
        'js/email-decode.min.js',
        'js/jquery-min.js',
        'js/popper.min.js',
        'js/bootstrap.min.js',
        'js/color-switcher.js',
        'js/jquery.mixitup.min.js',
        'js/jquery.inview.js',
        'js/jquery.counterup.min.js',
        'js/scroll-top.js',
        'js/material.min.js',
        'js/ripples.min.js',
        'js/owl.carousel.min.js',
        'js/form-validator.min.js',
        'js/contact-form-script.min.js',
        'js/wow.js',
        'js/jquery.magnific-popup.min.js',
        'js/jquery.slicknav.js',
        'js/main.js',
        'js/fileupload/jquery.ui.widget.min.js',
        'js/fileupload/jquery.iframe-transport.js',
        'js/fileupload/jquery.fileupload.js',
        'js/jquery.lazy.min.js'
        ])
        .pipe(concat('main.js'))
        .pipe(gulp.dest('global/build/js'));
});

gulp.task('default', ['pack-js']);