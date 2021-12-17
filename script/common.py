#! /usr/bin/env python3
import argparse, build_utils, contextlib, functools, os, pathlib, platform, re, shutil, subprocess, sys, time, urllib.request, zipfile

basedir = os.path.abspath(os.path.dirname(__file__) + '/..')
if build_utils.system == 'macos':
  classifier = 'macos-' + build_utils.arch
  module = 'io.github.humbleui.skija.macos.' + build_utils.arch
else:
  classifier = build_utils.system
  module = 'io.github.humbleui.skija.' + build_utils.system

@functools.cache
def deps_run():
  return [build_utils.fetch_maven('io.github.humbleui', 'types', '0.1.0')]

@functools.cache
def deps_compile():
  return [
    build_utils.fetch_maven('org.projectlombok', 'lombok', '1.18.22'),
    build_utils.fetch_maven('org.jetbrains', 'annotations', '20.1.0')
  ] + deps_run()

@functools.cache
def version():
  parser = argparse.ArgumentParser()
  parser.add_argument('--version')
  (args, _) = parser.parse_known_args()
  if args.version:
    return args.version

  ref = os.getenv('GITHUB_REF')
  if ref and ref.startswith('refs/tags/'):
    return ref[len('refs/tags/'):]

  ref = os.getenv('GITHUB_SHA')
  if ref:
    return ref[:10]

  return '0.0.0'