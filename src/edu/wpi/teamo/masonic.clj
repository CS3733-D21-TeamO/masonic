(ns edu.wpi.teamo.masonic
  (:require 
   [edu.wpi.teamo.masonic.api :as api]
   [edu.wpi.teamo.masonic.server :as server]
   [integrant.core :as ig])
  (:import edu.wpi.teamo.Main)
  (:gen-class))

(def config
  {::server/http   {::server/port 3000
                    ::server/env  (ig/ref ::api/env)}
   ::api/env       {}
   ::project-mason {}})

(def system (volatile! nil))

(defmethod ig/init-key ::project-mason [_ _]
  (future
    (try (Main/main (make-array String 0))
         (catch Exception e))
    (System/exit 0)))

(defmethod ig/halt-key! ::project-mason [_ f]
  (future-cancel f))

(defn start!
  ([args] (start!))
  ([]
   (when @system
     (vswap! system ig/halt!))
   (vreset! system (ig/init config))))

(defn start-headless!
  ([args] (start-headless!))
  ([]
   (when @system
     (vswap! system ig/halt!))
   (vreset! system (ig/init config [::server/http ::api/env]))))

(defn -main [& args]
  (start!))

(comment
  (start!)
  (start-headless!))
