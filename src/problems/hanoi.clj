(ns problems.hanoi
  (require [clojure.set :as set]))

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

(def towersinside [[5 4 3 2 1] [] [] [] [] []])
(def towers (->State towersinside))
(println towersinside)
(println (count (nth (:towers towers) 0)))

(defn children [state]
  (for [[old new] [[0, 1] [1, 0], [0, 2], [1, 2] [2, 1], [2, 0], ;4td tower->
                   [0, 3], [1, 3], [2, 3], [3, 0], [3,1], [3,2], ;5th tower ->
                   [0, 4], [1, 4], [2, 4], [3, 4], [4, 0], [4, 1], [4, 2], [4,3]]
        :when (legal? (:towers state)  old new)]
    (->State (swap (:towers state) old new))))

(defn prefer-horizontal-cost [old new]
      (if (empty? (or old new)) 0 1))

;; (defn state->vec [state]
;;   (let [T1 (count (nth (:towers state) 0))
;;         T2 (count (nth (:towers state) 1))
;;         T3 (count (nth (:towers state) 2))
;;         T4 (count (nth (:towers state) 3))
;;         T5 (count (nth (:towers state) 4))]
;;     (map / [T1 T2 T3 T4 T5] 15)))





;; (defn num-non-blank-wrong [goal-state current-state]
;;   (count
