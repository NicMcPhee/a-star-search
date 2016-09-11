(ns problems.chinese-ring-puzzle)

(defrecord State [leadRing Rings])

(defn children [state]
  (let [leadRing (:leadRing state)
        Rings (:Rings state)
        nextRing (+ leadRing 1)
        state1 (->State state)
        state2 (->State state)]
    (if (= (nth (:Rings state) 0) 1)
                                      (let [(nth (:Rings state1) 0) 0,
                                           (:leadRing state1) (.indexOf (:Rings state1) 1)])
                                                                                              (let [(nth (:Rings state1) 0) 1,
                                                                                                    (:leadRing state1) 0]))

    (if (<= nextRing 3) (if (= (nth (:Rings state) nextRing) 1)
                                                                (let [(nth (:Rings state2) nextRing) 0,
                                                                      (:leadRing state1) (.indexOf (:Rings state1) 1)])
                                                                                                                        (let [(nth (:Rings state2) nextRing) 1,
                                                                                                                              (:leadRing state1) (.indexOf (:Rings state1) 1)]))
      (->State state1)
      (->State state2)))

