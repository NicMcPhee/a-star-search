(ns a-star-search.repl
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.pprint :as pp]
            [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            ;[problems.yours :as abbrv]
             ))

;;this is a file to make repl development/debugging easier. It gathers useful
;;repl tools, and some basic info about namespaces.

;The 'canonical' way to load a file into the repl is

;(load-file "src/foo/bar.clj")

  ; this grabs & compiles that file, allowing you to reference the namespace

;(use 'foo.bar)

  ; this adds the contents of the foo.bar namespace literally (no bar/function
  ; stuff required.

;However, simply starting a lein repl (in a project!) usually loads all of the
; files - but you can't access the functions because you haven't required the
; namespaces you want to access. Because clojure is super cool, you /can/ use ns
; in the repl, but that's super clunky and prone to error.

;What you really want to do is to use in-ns like this:

  ;(in-ns 'foo.bar)

;Given that you've already loaded the repl, in-ns can see any namespace in the
; project directory. in-ns just changes your current namespace in the repl to the
; given namespace. You do have to be careful; in-ns will make the namespace if it
; doesn't exist. If you need to load a new file/namespace or the
; changes to a file/namespace you should use

  ;(refresh)

;refresh reloads files, namespaces and undoes most (ns ...) shenanigans. It isn't
; part of clojure core, so you do have to import it. It's imported at the top of
; this file as an example - just copy paste the vector into your :require
; statements.

;a common workflow is to create a repl file which already has all of the
; namespaces you might need in a single place. It's also a useful as a scratchpad
; and as a space for development tools - a function that prints your state in a
; useful way, for instance. Or a pretty printer

(pp/pprint {:val "Pretty prints objects!"})