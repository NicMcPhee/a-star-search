(ns hanoi)

(defrecord State [[tower-start] [tower-mid] [tower-finish]])

(defn legal? [[tower1] [tower2]]
  (and (< (peek [tower1]) (peek[tower2]))))

(defn move [[tower1] [tower2]]
  (let [xx ()
        yy ()]
    (assoc-in (assoc-in space_)
                        )))

(defn children [state]
  (let