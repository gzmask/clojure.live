(ns app
  (:require [sci.core :as sci]))

(defn init [] (println "hello world cljs"))

(sci/eval-string "(println \"hello sci\")"
                 {:bindings {'println println}})

(sci/eval-string "(defn a [] :a) (a)")







