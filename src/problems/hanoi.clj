(ns problems.hanoi
  (require [clojure.set :as set]))

;;scardycat.clj was a practice file; this file is (hanoi.clj) our final submission

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

;; (def towersinside [[5 4 3 2 1] [] [] [] [] []])
;; (def towers (->State towersinside))

(defn children [state]
  (for [[old new] (possible-swaps state)
        :when (legal? (:towers state)  old new)]
    (->State (swap (:towers state) old new))))

;;prefer-horizontal-cost is not working; we couldn't figure out how to get it to work since
;;state is only one array of three arrays
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

;; (defn prefer-horizontal-cost [s t]
;;   (let [s-x (first (:blank-position s))
;;         t-x (first (:blank-position t))]
;;     (inc (Math/abs (- s-x t-x)))))

;; (possible-swaps towers)
