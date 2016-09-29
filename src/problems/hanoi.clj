(ns problems.hanoi
  (require [clojure.set :as set]))

(defrecord State [towers])

(defn legal? [towers old-t new-t]
  (and (not (empty? (nth towers old-t)))
       (if (empty? (nth towers new-t))
         true
         (< (peek (nth towers old-t)) (peek (nth towers new-t))))))

(defn cartesian-x [colls]
    (if (empty? colls)
    '(())
    (for [x (first colls)
          more (cartesian-x (rest colls))]
      (cons x more))))

(defn possible-swaps [state]
  (let [num (count (:towers state))
        coll1 (range num)
        coll2 (range num)
        colls (list coll1 coll2)]
    (cartesian-x colls)))

(defn swap [towers old-t new-t]
  (let [disk (peek (nth towers old-t))
        new-old (pop (nth towers old-t))
        new-new (conj (nth towers new-t) disk)]
    (assoc (assoc towers old-t new-old) new-t new-new)))


(defn children [state]
  (for [[old new] (possible-swaps state)
        :when (legal? (:towers state)  old new)]
    (->State (swap (:towers state) old new))))


;;Causes the prefer-horizontal-cost to run as a Breadth-First-Search.
(defn prefer-horizontal-cost [old new]
      (if (empty? (or old new)) 0 1))

;;Tries to reward the state for being closer to the end-state.
(defn spicy [current-state goal-state]
   (+ (/ (* 100 (reduce + (nth (:towers current-state) 2)))
        (* 100 (count (nth (:towers current-state) 2))))
     (count (nth (:towers current-state) 2))))

(defn state->vec [state]
  (flatten (:board state)))

(defn too-many-piles? [[x y]]
  (let [num (count y)
        num-of-piles (count (filter (fn [var] (> (count (drop-last y)) 2))))]
      (> num-of-piles 3)))


(defn num-non-blank-wrong [goal-state current-state]
  (count (remove too-many-piles?
                 (map (fn [x y] [x y])
                      (state->vec goal-state)
                      (state->vec current-state)))))

