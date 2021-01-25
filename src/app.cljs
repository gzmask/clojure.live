(ns app
  (:require
   [babylonjs :refer [Engine Scene]]
   [sci.core :as sci]
   [edamame.core :refer [parse-string]]
   [cljs.js :as cljs]
   [cljs.env :as env]
   [shadow.cljs.bootstrap.browser :as boot]))
;;import { Engine, Scene, ArcRotateCamera, Vector3, HemisphericLight, Mesh, MeshBuilder } from "@babylonjs/core";
;;import { Scene, Engine } from 'babylonjs';
(defonce compile-state-ref (env/default-compiler-env))

(defn init []
  (boot/init compile-state-ref
             {:path "/out/bootstrap"}
             (fn -test-compile-code []
               (cljs/eval-str
                compile-state-ref
                "(println \"hello bootstrap\")"
                "[test]"
                {:eval cljs/js-eval
                 :load (partial boot/load compile-state-ref)}
                prn))))

(defn eval
  ([code-str]
   (eval code-str {}))
  ([code-str opts]
   (cljs/eval compile-state-ref
              (parse-string code-str)
              {:eval cljs/js-eval
               :ns (or (:ns opts) 'app)
               :load (partial boot/load compile-state-ref)}
              prn)))

(comment 

  ;;sci sandboxed evals
  (sci/eval-string "(println \"hello sci\")"
                   {:bindings {'println println}})

  (sci/eval-string "(defn a [] :a) (a)")

  ;;bootstrapped cljs evals
  (cljs/eval-str compile-state-ref
                 "(println \"hello bootstrap\")"
                 "test"
                 {:eval cljs/js-eval
                  :load (partial boot/load compile-state-ref)}
                 prn)

  (cljs/eval compile-state-ref
             (parse-string "(def a 1)")
             {:eval cljs/js-eval
              :ns 'app
              :load (partial boot/load compile-state-ref)}
              prn)

  (cljs/eval compile-state-ref
             (parse-string "(inc a)")
             {:eval cljs/js-eval
              :ns 'app
              :load (partial boot/load compile-state-ref)}
              prn)

  ;;shortname
  (eval "(js/alert \"yo\")")


  )







