; we used the two rules of chinese ring puzzle:
; 1) the first ring, which is hooked in the very front of the loop, can be taken off or put back on the loop any time;
; 2) the only other ring that can be taken off from or put back on the loop is the ring next to the first ring.
(ns problems.chinese-ring-puzzle)

(defrecord State [leadRing Rings num-rings-off last-ring-off])

(defn children [state]
  (let [len (count (:Rings state)),
        leadRing (:leadRing state),
        Rings (:Rings state),
        nextRing (+ leadRing 1),
        R1 (nth Rings 0),
        R2 (nth Rings 1),
        R3 (nth Rings 2),
        R4 (nth Rings 3),
        R5 (nth Rings 4),
        R6 (nth Rings 5),
        R7 (nth Rings 6),
        R8 (nth Rings 7),
        R9 (nth Rings 8)]


    (let [children (if (= R1 "on")
        (let [Rings1 ["off" R2 R3 R4 R5 R6 R7 R8 R9]
              leadRing1 (.indexOf Rings1 "on")
              num-rings-off-1 (count (filter #(= "off" %) Rings1))
              last-ring-off-1 (.lastIndexOf Rings1 "off")]
              [(->State leadRing1 Rings1 num-rings-off-1 last-ring-off-1)])
        (let [Rings1 ["on" R2 R3 R4 R5 R6 R7 R8 R9]
              leadRing1 (.indexOf Rings1 "on")
              num-rings-off-1 (count (filter #(= "off" %) Rings1))
              last-ring-off-1 (.lastIndexOf Rings1 "off")]
              [(->State leadRing1 Rings1 num-rings-off-1 last-ring-off-1)]))]


    (if (< nextRing len)
        (if (= (get Rings nextRing) "on")
            (let [Rings2 (assoc Rings nextRing "off")
                  leadRing2 (.indexOf Rings2 "on")
                  num-rings-off-2 (count (filter #(= "off" %) Rings2))
                  last-ring-off-2 (.lastIndexOf Rings2 "off")]
                  (conj children (->State leadRing2 Rings2 num-rings-off-2 last-ring-off-2)))
            (let [Rings2 (assoc Rings nextRing "on")
                  leadRing2 (.indexOf Rings2 "on")
                  num-rings-off-2 (count (filter #(= "off" %) Rings2))
                  last-ring-off-2 (.lastIndexOf Rings2 "off")]
                  (conj children (->State leadRing2 Rings2 num-rings-off-2 last-ring-off-2))))
            children))))



