(ns edu.wpi.teamo.masonic.api
  (:require [com.wsscode.pathom.viz.ws-connector.core :as pvc]
            [com.wsscode.pathom.viz.ws-connector.pathom3 :as p.connector]
            [com.wsscode.pathom3.connect.indexes :as pci]
            [edu.wpi.teamo.masonic.types :as types]
            [edu.wpi.teamo.masonic.account :as account]
            [edu.wpi.teamo.masonic.map :as map]
            [edu.wpi.teamo.masonic.map.edge :as edge]
            [edu.wpi.teamo.masonic.map.node :as node]
            [edu.wpi.teamo.masonic.request.laundry :as laundry]
            [edu.wpi.teamo.masonic.request.medicine :as medicine]
            [edu.wpi.teamo.masonic.request.sanitation :as sanitation]
            [edu.wpi.teamo.masonic.session :as session]
            [integrant.core :as ig]))

(types/install!)

(defmethod ig/init-key ::env [_ {}]
  (-> (pci/register [node/all node/by-id edge/all edge/by-id map/db medicine/all medicine/upsert
                     sanitation/all sanitation/upsert laundry/all laundry/upsert account/by-username
                     account/all account/full-name session/login])
      (p.connector/connect-env {::pvc/parser-id ::env})))

