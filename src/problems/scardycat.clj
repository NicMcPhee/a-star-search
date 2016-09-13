(ns hanoi)

(defrecord State [[tower-start] [tower-mid] [tower-finish]]);;rename before Wednesday @ 2200!

(defn legal? [[tower1] [tower2]]
  (and (< (peek [tower1]) (peek[tower2]))))













;;(defn move [[tower1] [tower2]]
;;  (let [old ()
;;        new ()]
;;    (assoc-in (assoc-in space_)
;;                        )))