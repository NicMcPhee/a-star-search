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

(defn children [state]
  (for [[old new] [[0, 1] [1, 0], [0, 2], [1, 2] [2, 1], [2, 0]]
        :when (legal? (:towers state)  old new)]
    (->State (swap (:towers state) old new))))

;;Does slightly more than a dead salesman. Just lets prefer-horizontal-cost run as a BFS.
(defn prefer-horizontal-cost [old new]
      (if (empty? (or old new)) 0 1))

;;
(defn spicy [current-state goal-state]
  (+ (/ (* 100 (reduce + (nth (:towers current-state) 2)))
        (count (nth (:towers current-state) 2)))
     (* 100 (count (nth (:towers current-state) 2)))))
