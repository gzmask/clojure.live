(ns app
  (:require
   [babylonjs :refer [Engine Scene] :as baby]
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

(defn my-eval
  ([code-str]
   (my-eval code-str {}))
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
             (parse-string "(ns app)")
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
  (my-eval "(js/alert \"yo\")")

  ;;babylon js
  (def canvas (.getElementById js/document "myCanvas"))
  (def engine (Engine. canvas true #js {:preserveDrawingBuffer true
                                        :stencil true}))
  (def scene (create-scene engine canvas))

  (.runRenderLoop engine #(.render scene))



  )

(defn create-scene [engine canvas]
  (let [scene (Scene. engine)
        camera (doto (baby/FreeCamera. "camera1" (baby/Vector3. 0 5 -10) scene)
                 (.setTarget (.Zero baby/Vector3))
                 (.attachControl canvas false))
        light (baby/HemisphericLight. "light1" (baby/Vector3. 0 1 0) scene)
        sphere (.CreateSphere baby/Mesh "sphere1" 16 2 scene false (.-FRONTSIDE baby/Mesh))
        _ (set! (.. sphere -position -y) 1)
        ground (.CreateGround baby/Mesh "ground1" 6 6 2 scene false)]
    scene))







