public enum SceneType {
        LOGIN_REGISTRATION,
        TAKE_QUIZ,
        SELECT_QUIZ;

        public boolean isStateless() {
            if(this.equals(LOGIN_REGISTRATION) || this.equals(SELECT_QUIZ)){
                return true;
            }
            return false;
        }
}
