"use client";

import Link from "next/link";
import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import { API_BASE_URL } from "@/config/api";

export default function RegisterPage() {
  const router = useRouter();

  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    setError("");

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    try {
      setLoading(true);

      const response = await fetch(
        "${API_BASE_URL}/auth/register",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
          },

          credentials: "include",

          body: JSON.stringify({
            fullName,
            email,
            password,
          }),
        }
      );

      if (!response.ok) {
        const data = await response.json().catch(() => null);

        setError(
          data?.message ||
            "Registration failed. Please try again."
        );

        return;
      }

      // Registration successful.
      // Redirect user to login page.
      router.push("/login?registered=true");

    } catch (err) {
      console.error(err);

      setError(
        "Unable to connect to Partaay. Please try again."
      );

    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth-page">

      <div className="auth-card">

        <Link href="/" className="brand">
          Partaay
        </Link>

        <h1>Create your account 🎊</h1>

        <p className="subtitle">
          Join Partaay and start discovering events around you.
        </p>

        <div className="social-buttons">

          <button
            type="button"
            className="social-button"
          >
            Sign up with Google
          </button>

          <button
            type="button"
            className="social-button"
          >
            Sign up with Facebook
          </button>

        </div>

        <div className="divider">
          <span>or register with email</span>
        </div>

        <form onSubmit={handleSubmit}>

          <div className="form-group">

            <label htmlFor="name">
              Full name
            </label>

            <input
              id="name"
              type="text"
              placeholder="Your full name"
              value={fullName}
              onChange={(e) =>
                setFullName(e.target.value)
              }
              required
            />

          </div>

          <div className="form-group">

            <label htmlFor="email">
              Email address
            </label>

            <input
              id="email"
              type="email"
              placeholder="you@example.com"
              value={email}
              onChange={(e) =>
                setEmail(e.target.value)
              }
              required
            />

          </div>

          <div className="form-group">

            <label htmlFor="password">
              Password
            </label>

            <div className="password-field">

              <input
                id="password"
                type={
                  showPassword
                    ? "text"
                    : "password"
                }
                placeholder="Create a password"
                value={password}
                onChange={(e) =>
                  setPassword(e.target.value)
                }
                minLength={8}
                required
              />

              <button
                type="button"
                className="show-password"
                onClick={() =>
                  setShowPassword(!showPassword)
                }
              >
                {showPassword ? "Hide" : "Show"}
              </button>

            </div>

          </div>

          <div className="form-group">

            <label htmlFor="confirmPassword">
              Confirm password
            </label>

            <input
              id="confirmPassword"
              type="password"
              placeholder="Confirm your password"
              value={confirmPassword}
              onChange={(e) =>
                setConfirmPassword(e.target.value)
              }
              required
            />

          </div>

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <label className="terms">

            <input
              type="checkbox"
              required
            />

            <span>
              I agree to the{" "}
              <Link href="#">
                Terms of Service
              </Link>{" "}
              and{" "}
              <Link href="#">
                Privacy Policy
              </Link>.
            </span>

          </label>

          <button
            type="submit"
            className="primary-button"
            disabled={loading}
          >
            {loading
              ? "Creating Account..."
              : "Create Account"}
          </button>

        </form>

        <p className="bottom-text">

          Already have an account?{" "}

          <Link href="/login">
            Sign in
          </Link>

        </p>

      </div>

    </main>
  );
}