(ns problems.chinese-ring-puzzle)

(defrecord State [leadRing Rings])

(defn children [state]
  (let [len (count (:Rings state)),
        leadRing (:leadRing state),
        Rings (:Rings state),
        nextRing (+ leadRing 1),
        R1 (nth (:Rings state) 0),
        R2 (nth (:Rings state) 1),
        R3 (nth (:Rings state) 2),
        R4 (nth (:Rings state) 3)]

    (if (= R1 1)
        (let [Rings1 (int-array [0 R2 R3 R4]), leadRing1 (aget Rings1 1)]
              (->State leadRing1 Rings1)),
        (let [Rings1 (int-array [1 R2 R3 R4]), leadRing1 (aget Rings1 1)]
              (->State leadRing1 Rings1)))


    (if (<= nextRing (- len 1))
        (if (= (nth (:Rings state) nextRing) 1),
            (let [Rings2 (assoc Rings nextRing 0), leadRing2 (aget Rings2 1)]
                  (->State leadRing2 Rings2)),
            (let [Rings2 (assoc Rings nextRing 1), leadRing2 (aget Rings2 1)]
                  (->State leadRing2 Rings2))))))


