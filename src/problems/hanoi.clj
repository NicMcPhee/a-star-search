(ns problems.hanoi
  (require [clojure.set :as set]))

;;scardycat.clj was a practice file; this file is (hanoi.clj) our final submission

(defrecord State [towers])

(defn legal? [towers old-t new-t]
  (and (not (empty? (nth towers old-t)))
       (if (empty? (nth towers new-t))
         true
         (< (peek (nth towers old-t)) (peek (nth towers new-t))))))

(defn swap [towers old-t new-t]
  (let [disk (peek (nth towers old-t))
        new-old (pop (nth towers old-t))
        new-new (conj (nth towers new-t) disk)]
    (assoc (assoc towers old-t new-old) new-t new-new)))
(def towersinside [[5 4 3 2 1] [] []])
(def towers (->State towersinside))

(defn children [state]
  (for [[x y] [[0, 1] [1, 0], [0, 2], [1, 2] [2, 1], [2, 0]]
        :let [old x
              new y]
        :when (legal? (:towers state)  old new)]
    (->State (swap (:towers state) old new))))

;;prefer-horizontal-cost is not working; we couldn't figure out how to get it to work since
;;state is only one array of three arrays

(defn prefer-horizontal-cost [s t]
  (let [s-x (if (empty? s) 1 (first s))
        t-x  (if (empty? t) 1 (first t))]
    (inc (Math/abs (- s-x t-x)))))
